package br.com.a2dm.spdm.omie.repository;

import java.math.BigInteger;
import java.util.List;

import br.com.a2dm.brcmn.dto.ClienteDTO;
import br.com.a2dm.brcmn.dto.ClienteIntegracaoDTO;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.omie.api.OmieApiClient;
import br.com.a2dm.spdm.omie.builder.OmieClienteBuilder;
import br.com.a2dm.spdm.omie.payload.AlterarPayloadCliente;
import br.com.a2dm.spdm.omie.payload.PesquisarPayloadCliente;
import br.com.a2dm.spdm.omie.payload.PesquisarPayloadClienteIntegracao;

public class OmieClientesRepository {

	private static OmieClientesRepository instance;
	
	private OmieClientesRepository() {
	}
	
	public static OmieClientesRepository getInstance() {
		if(instance == null) {
			instance = new OmieClientesRepository();
		}
		return instance;
	}
	
	public ClienteIntegracaoDTO pesquisarCliente(BigInteger idExternoOmie) throws OmieRepositoryException {
		try {
			PesquisarPayloadClienteIntegracao pesquisarPedidoOmie = new OmieClienteBuilder().buildPesquisarClientePorIntegracao(idExternoOmie);
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/geral/clientes/", "ConsultarCliente", pesquisarPedidoOmie);
			return new OmieClienteBuilder().buildPesquisarClientePorIntegracaoResponse(response.getBody());
		} catch (Exception e) {
			throw new OmieRepositoryException(e);
		}
	}
	
	public List<ClienteDTO> pesquisarClientes(String nomeCliente) throws OmieRepositoryException {
		try {
			PesquisarPayloadCliente pesquisarPedidoOmie = new OmieClienteBuilder().buildPesquisarCliente(nomeCliente);
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/geral/clientes/", "ListarClientesResumido", pesquisarPedidoOmie);
			return new OmieClienteBuilder().buildPesquisarClienteResponse(response.getBody());
		} catch (Exception e) {
			throw new OmieRepositoryException(e);
		}
	}
	
	public BigInteger alterarCliente(ClienteDTO clienteDto) throws OmieRepositoryException {
		try {
			AlterarPayloadCliente alterarClienteOmie = new OmieClienteBuilder().buildAlterarCliente(clienteDto);
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/geral/clientes/", "AlterarCliente", alterarClienteOmie);
			return new OmieClienteBuilder().buildAlterarClienteResponse(response.getBody());
		} catch (Exception e) {
			throw new OmieRepositoryException(e);
		}
	}
}
