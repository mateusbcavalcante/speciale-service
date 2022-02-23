package br.com.a2dm.spdm.omie.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.a2dm.brcmn.dto.ClienteDTO;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.omie.payload.ClienteWebhookPayload;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayload;
import br.com.a2dm.spdm.omie.payload.TagPayload;
import br.com.a2dm.spdm.omie.repository.OmieClientesRepository;
import br.com.a2dm.spdm.service.ClienteService;

public class OmieClienteService {

	private static OmieClienteService instance;

	private OmieClienteService() {
	}

	public static OmieClienteService getInstance() {
		if (instance == null) {
			instance = new OmieClienteService();
		}
		return instance;
	}
	
	public List<String> pesquisarClientesList(List<String> clientes) throws OmieServiceException {
		try {
			List<String> clientesExistentes = new ArrayList<>();
			for (String cliente : clientes) {
				List<ClienteDTO> clientesDTO = this.pesquisarClientes(cliente);
				if (clientesDTO != null && clientesDTO.size() > 0) {
					for (ClienteDTO element : clientesDTO) {
						clientesExistentes.add(element.getNomeCliente());
					}
				}
			}
			return clientesExistentes;
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}

	public List<ClienteDTO> pesquisarClientes(String nomeCliente) throws OmieServiceException {
		try {
			return OmieClientesRepository.getInstance().pesquisarClientes(nomeCliente);
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}
	
	public List<BigInteger> alterarClientes(List<String> clientes) throws OmieServiceException {
		try {
			List<BigInteger> listCodigoClienteOmie = new ArrayList<>();
			
			for (String cliente : clientes) {
				List<ClienteDTO> listClienteDto = this.pesquisarClientes(cliente);
				
				for (ClienteDTO clienteDto : listClienteDto) {
					BigInteger codigoClienteOmie = OmieClientesRepository.getInstance().alterarCliente(clienteDto);
					
					if (codigoClienteOmie != null && codigoClienteOmie.intValue() > 0) {					
						listCodigoClienteOmie.add(codigoClienteOmie);
					}
				}
			}
			return listCodigoClienteOmie;
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}
	
	public void processar(ClienteWebhookPayload clienteWebhookPayload) throws OmieServiceException, IOException {
		
		try {
			
			String nomeTabelaPreco = "TP " + clienteWebhookPayload.getEvent().getNome_fantasia();
			TabelaPrecoPayload tabelaPreco = OmieTabelaPrecoService.getInstance().obterTabelaPreco(nomeTabelaPreco);
			
			Cliente cliente = new Cliente();
			cliente.setIdExternoOmie(clienteWebhookPayload.getEvent().getCodigo_cliente_omie());

			cliente = ClienteService.getInstancia().get(cliente, 0);

			if (cliente == null) {
				cliente = new Cliente();
				
				cliente.setFlgAtivo("S");
				cliente.setDesCliente(clienteWebhookPayload.getEvent().getNome_fantasia());

				List<Cliente> clientes = ClienteService.getInstancia().pesquisar(cliente, 0);

				if (clientes == null
						|| clientes.size() <= 0) {
					inserirCliente(clienteWebhookPayload, tabelaPreco);
				} else if (clientes.size() == 1) {
					alterarCliente(clienteWebhookPayload, clientes.get(0), tabelaPreco);
				}
			} else {
				alterarCliente(clienteWebhookPayload, cliente, tabelaPreco);
			}
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}
	
	private String obterTabelaPreco(List<TagPayload> tags) {
		for (TagPayload tag: tags) {
			if (isTagTabelaPreco(tag)) {
				return tag.getTag();
			}
		}
		return null;
	}

	private void inserirCliente(ClienteWebhookPayload clienteWebhookPayload, TabelaPrecoPayload tabelaPreco) throws Exception {
		Cliente clienteInsert = new Cliente();
		clienteInsert.setDesCliente(clienteWebhookPayload.getEvent().getNome_fantasia());
		clienteInsert.setIdExternoOmie(clienteWebhookPayload.getEvent().getCodigo_cliente_omie());
		
		if (clienteWebhookPayload.getEvent().getRecomendacoes() != null) {
			if (clienteWebhookPayload.getEvent().getRecomendacoes().getCodigo_vendedor() != null
					&& clienteWebhookPayload.getEvent().getRecomendacoes().getCodigo_vendedor().intValue() > 0) {
				clienteInsert.setCodVendedor(clienteWebhookPayload.getEvent().getRecomendacoes().getCodigo_vendedor());
			}
			
			if (clienteWebhookPayload.getEvent().getRecomendacoes().getNumero_parcelas() != null
					&& !clienteWebhookPayload.getEvent().getRecomendacoes().getNumero_parcelas().equalsIgnoreCase("")) {
				clienteInsert.setCodParcelas(clienteWebhookPayload.getEvent().getRecomendacoes().getNumero_parcelas());
			}
		}
		
		if (tabelaPreco != null 
				&& tabelaPreco.getnCodTabPreco() != null 
				&& tabelaPreco.getnCodTabPreco().intValue() > 0) 
		{
			clienteInsert.setIdTabelaPrecoOmie(tabelaPreco.getnCodTabPreco());
		}
		clienteInsert.setFlgAtivo("S");
		clienteInsert.setDatCadastro(new Date());
		clienteInsert.setIdUsuarioCad(new BigInteger("1"));
		clienteInsert.setHorLimite("06:00");
		ClienteService.getInstancia().inserir(clienteInsert);
	}

	private void alterarCliente(ClienteWebhookPayload clienteWebhookPayload, Cliente cliente, TabelaPrecoPayload tabelaPreco) throws Exception {
		cliente.setDesCliente(clienteWebhookPayload.getEvent().getNome_fantasia());
		cliente.setIdExternoOmie(clienteWebhookPayload.getEvent().getCodigo_cliente_omie());
		cliente.setFlgAtivo("S");

		if (clienteWebhookPayload.getEvent().getRecomendacoes() != null) {
			if (clienteWebhookPayload.getEvent().getRecomendacoes().getCodigo_vendedor() != null
					&& clienteWebhookPayload.getEvent().getRecomendacoes().getCodigo_vendedor().intValue() > 0) {
				cliente.setCodVendedor(clienteWebhookPayload.getEvent().getRecomendacoes().getCodigo_vendedor());
			}
			
			if (clienteWebhookPayload.getEvent().getRecomendacoes().getNumero_parcelas() != null
					&& !clienteWebhookPayload.getEvent().getRecomendacoes().getNumero_parcelas().equalsIgnoreCase("")) {
				cliente.setCodParcelas(clienteWebhookPayload.getEvent().getRecomendacoes().getNumero_parcelas());
			}
		}
		
		if (tabelaPreco != null 
				&& tabelaPreco.getnCodTabPreco() != null 
				&& tabelaPreco.getnCodTabPreco().intValue() > 0) 
		{
			cliente.setIdTabelaPrecoOmie(tabelaPreco.getnCodTabPreco());
		}
		if (isTopicExcluido(clienteWebhookPayload)) {
			cliente.setFlgAtivo("N");
		}
		ClienteService.getInstancia().alterar(cliente);
	}
	
	private boolean isTagTabelaPreco(TagPayload tag) {
		return tag.getTag().indexOf("TP") == 0;
	}

	private boolean isTopicExcluido(ClienteWebhookPayload clienteWebhookPayload) {
		return clienteWebhookPayload.getTopic().equalsIgnoreCase("ClienteFornecedor.Excluido");
	}
}
