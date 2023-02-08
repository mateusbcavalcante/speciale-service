package br.com.a2dm.spdm.omie.service;

import java.math.BigInteger;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.a2dm.brcmn.dto.ClienteIntegracaoDTO;
import br.com.a2dm.brcmn.dto.PedidoDTO;
import br.com.a2dm.brcmn.dto.ProdutoDTO;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.entity.Feriado;
import br.com.a2dm.spdm.omie.repository.OmiePedidoRepository;
import br.com.a2dm.spdm.omie.repository.OmieRepositoryException;
import br.com.a2dm.spdm.service.ClienteService;
import br.com.a2dm.spdm.service.FeriadoService;
import br.com.a2dm.spdm.utils.DateUtils;

public class OmiePedidoService {

	private static OmiePedidoService instance;

	private OmiePedidoService() {
	}

	public static OmiePedidoService getInstance() {
		if (instance == null) {
			instance = new OmiePedidoService();
		}
		return instance;
	}
	
	public PedidoDTO pesquisarPedido(BigInteger idCliente, BigInteger idPedido, String dataPedido) throws OmieServiceException {
		try {
			Cliente cliente = new Cliente();
			cliente.setIdCliente(idCliente);
			cliente = ClienteService.getInstancia().get(cliente, 0);
			PedidoDTO pedidoDTO = OmiePedidoRepository.getInstance().pesquisarPedidoCliente(idCliente, cliente, idPedido, dataPedido);
			
			if (pedidoDTO == null) {
				throw new OmieRepositoryException("Não existe pedido para o(s) filtro(s) informado(s).");
			}
			
			return pedidoDTO;
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}

	public PedidoDTO cadastrarPedido(PedidoDTO pedidoDTO) throws OmieServiceException {
		try {
			Cliente cliente = new Cliente();
			cliente.setIdCliente(pedidoDTO.getIdCliente());
			cliente = ClienteService.getInstancia().get(cliente, 0);
			pedidoDTO.setIdExternoOmie(cliente.getIdExternoOmie());
			pedidoDTO.setCodVend(cliente.getCodVendedor());
			pedidoDTO.setCodParcelas(cliente.getCodParcelas());
			this.removerCaracteresEspeciais(pedidoDTO);
			this.validarAtivo(cliente);
			this.validarDuplicidade(pedidoDTO);
			
			if (!isAdmin(pedidoDTO)) {
				this.validarFeriado(pedidoDTO);
				this.validarFinalDeSemana(pedidoDTO);
				this.validarHorarioLimite(cliente, pedidoDTO);
				this.validarLimitePaes(pedidoDTO);
			}
			
			ClienteIntegracaoDTO clienteIntegracaoDTO = OmieClienteService.getInstance().pesquisarCliente(cliente.getIdExternoOmie());
			
			return OmiePedidoRepository.getInstance().cadastrarPedidoCliente(pedidoDTO, clienteIntegracaoDTO);
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}

	private boolean isAdmin(PedidoDTO pedidoDTO) {
		return pedidoDTO.isAdmin() == true;
	}
	
	public PedidoDTO alterarPedido(PedidoDTO pedidoDTO) throws OmieServiceException {
		try {
			Cliente cliente = new Cliente();
			cliente.setIdCliente(pedidoDTO.getIdCliente());
			cliente = ClienteService.getInstancia().get(cliente, 0);
			pedidoDTO.setIdExternoOmie(cliente.getIdExternoOmie());
			pedidoDTO.setCodVend(cliente.getCodVendedor());
			pedidoDTO.setCodParcelas(cliente.getCodParcelas());
			this.removerCaracteresEspeciais(pedidoDTO);
			this.validarAtivo(cliente);
			this.validarDuplicidade(pedidoDTO);
			
			if (!isAdmin(pedidoDTO)) {
				this.validarFeriado(pedidoDTO);
				this.validarFinalDeSemana(pedidoDTO);
				this.validarHorarioLimite(cliente, pedidoDTO);
				this.validarLimitePaes(pedidoDTO);
			}
			
			ClienteIntegracaoDTO clienteIntegracaoDTO = OmieClienteService.getInstance().pesquisarCliente(cliente.getIdExternoOmie());
			
			return OmiePedidoRepository.getInstance().alterarPedidoCliente(pedidoDTO, clienteIntegracaoDTO);
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}
	
	public void removerCaracteresEspeciais(PedidoDTO pedidoDTO) throws OmieServiceException {
		if (pedidoDTO.getObservacao() != null && !pedidoDTO.getObservacao().equalsIgnoreCase("")) {			
			pedidoDTO.setObservacao(Normalizer.normalize(pedidoDTO.getObservacao(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
		}
	}
	
	public void inativarPedido(BigInteger idCliente, PedidoDTO pedidoDTO) throws OmieServiceException {
		try {
			OmiePedidoRepository.getInstance().inativarPedido(pedidoDTO.getCodigoPedidoIntegracao(), pedidoDTO.getCodigoPedido());
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}
	
	private void validarAtivo(Cliente cliente) throws Exception {
		if (cliente.getFlgAtivo() != null && 
				cliente.getFlgAtivo().equalsIgnoreCase("N")) {
			throw new OmieRepositoryException("Não é possível realizar pedido. Por favor, entre em contato com os responsáveis para saber o motivo.");
		}
	}
	
	private void validarDuplicidade(PedidoDTO pedidoDTOReq) throws Exception {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(pedidoDTOReq.getIdCliente());
		cliente = ClienteService.getInstancia().get(cliente, 0);
		PedidoDTO pedidoDTO = OmiePedidoRepository.getInstance().pesquisarPedidoCliente(pedidoDTOReq.getIdCliente(), cliente, null, DateUtils.formatDate(pedidoDTOReq.getDataPedido(), "yyyy-MM-dd"));
		
		if (pedidoDTO != null && 
				(pedidoDTOReq.getCodigoPedidoIntegracao() == null) || (pedidoDTOReq.getCodigoPedidoIntegracao() != null && pedidoDTOReq.getCodigoPedidoIntegracao().longValue() != pedidoDTO.getCodigoPedidoIntegracao().longValue())) {
			throw new OmieRepositoryException("Já existe um pedido para a data informada.");
		}
	}
	
	private void validarFeriado(PedidoDTO pedidoDTO) throws Exception {
		Feriado feriado = new Feriado();
		feriado.setFlgAtivo("S");
		
		Calendar cLim = Calendar.getInstance();
		cLim.setTime(pedidoDTO.getDataPedido());
		
		Calendar c = Calendar.getInstance();
		c.setTime(pedidoDTO.getDataPedido());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		Date dataPedido = c.getTime();
		
		feriado.setDatFeriado(dataPedido);
		
		List<Feriado> listFeriado = FeriadoService.getInstancia().pesquisar(feriado, 0);
		
		if (listFeriado != null
				&& listFeriado.size() > 0) {
			throw new OmieRepositoryException("Não é possível realizar pedido, pois o dia informado é feriado.");
		}
	}
	
	private void validarFinalDeSemana(PedidoDTO pedidoDTO) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(pedidoDTO.getDataPedido());
		
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		
		if (dia == Calendar.SUNDAY || dia == Calendar.SATURDAY) {
			throw new OmieRepositoryException("Não é possível realizar pedido para sábado e/ou domingo.");
		}			
	}
	
	private void validarHorarioLimite(Cliente cliente, PedidoDTO pedidoDTO) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
		Date data = sdf.parse(cliente.getHorLimite());
		Calendar cLim = Calendar.getInstance();
		cLim.setTime(data);
		
		Calendar c = Calendar.getInstance();
		c.setTime(pedidoDTO.getDataPedido());
		c.set(Calendar.HOUR_OF_DAY, cLim.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, cLim.get(Calendar.MINUTE));
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		Date dataLimite = c.getTime();
		
		Date dataAtual = new Date();
		
		if (dataAtual.after(dataLimite)) {
			throw new OmieRepositoryException("A hora limite para realizar pedido foi ultrapassada! Hora limite: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataLimite));
		}
	}
	
	private void validarLimitePaes(PedidoDTO pedidoDTO) throws Exception {
		BigInteger qtdTotalSolicitada = BigInteger.ZERO;
		
		for (ProdutoDTO produto : pedidoDTO.getProdutos()) {
			qtdTotalSolicitada = qtdTotalSolicitada.add(produto.getQtdSolicitada());
		}
		
		if (!isClienteEvento(pedidoDTO) && !isMaster(pedidoDTO) && qtdTotalSolicitada.intValue() < 36) {
			throw new Exception("É necessário solicitar, no mínimo, 36 pacotes!");
		}
	}
	
	private boolean isClienteEvento(PedidoDTO pedidoDTO) throws Exception {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(pedidoDTO.getIdCliente());
		cliente = ClienteService.getInstancia().get(cliente, 0);
		
		if (cliente != null) {
			return cliente.getFlgEvento() != null && !cliente.getFlgEvento().equalsIgnoreCase("") && cliente.getFlgEvento().equalsIgnoreCase("S");
		}
		return false;
	}
	
	private boolean isMaster(PedidoDTO pedidoDTO) throws Exception {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(pedidoDTO.getIdCliente());
		cliente = ClienteService.getInstancia().get(cliente, 0);
		
		if (cliente != null) {
			return cliente.getFlgMaster() != null && !cliente.getFlgMaster().equalsIgnoreCase("") && cliente.getFlgMaster().equalsIgnoreCase("S");
		}
		return false;
	}
}
