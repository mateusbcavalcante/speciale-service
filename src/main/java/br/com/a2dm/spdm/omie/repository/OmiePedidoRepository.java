package br.com.a2dm.spdm.omie.repository;

import java.math.BigInteger;

import org.codehaus.jettison.json.JSONObject;

import br.com.a2dm.brcmn.dto.ClienteIntegracaoDTO;
import br.com.a2dm.brcmn.dto.PedidoDTO;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.omie.api.OmieApiClient;
import br.com.a2dm.spdm.omie.builder.OmiePedidoBuilder;
import br.com.a2dm.spdm.omie.payload.InativarPayloadPedido;
import br.com.a2dm.spdm.omie.payload.PedidoPayload;
import br.com.a2dm.spdm.omie.payload.PesquisarPayloadPedido;
import br.com.a2dm.spdm.utils.JsonUtils;

public class OmiePedidoRepository {

	private static OmiePedidoRepository instance;
	
	private OmiePedidoRepository() {
	}
	
	public static OmiePedidoRepository getInstance() {
		if(instance == null) {
			instance = new OmiePedidoRepository();
		}
		return instance;
	}
	
	public PedidoDTO pesquisarPedidoCliente(BigInteger idCliente, Cliente cliente, BigInteger idPedido, String dataPedido) throws OmieRepositoryException {
		try {
			return this.pesquisarPedido(idCliente, cliente.getIdExternoOmie(), idPedido, dataPedido);
		} catch (Exception e) {
			throw new OmieRepositoryException(e);
		}
	}
	
	protected PedidoDTO pesquisarPedido(BigInteger idCliente, BigInteger idExternoOmie, BigInteger idPedido, String dataPedido) throws OmieRepositoryException {
		try {
			PesquisarPayloadPedido pesquisarPedidoOmie = new OmiePedidoBuilder().buildPesquisarPedido(idExternoOmie, idPedido, dataPedido);
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/produtos/pedido/", "ListarPedidos", pesquisarPedidoOmie);
			return new OmiePedidoBuilder().buildPesquisarPedidoResponse(response.getBody(), idCliente);
		} catch (Exception e) {
			throw new OmieRepositoryException(e);
		}
	}
	
	public PedidoDTO cadastrarPedidoCliente(PedidoDTO pedidoDTO, ClienteIntegracaoDTO clienteIntegracaoDTO) throws OmieRepositoryException {
		try {
			return this.cadastrarPedido(pedidoDTO, clienteIntegracaoDTO);
		} catch (Exception e) {
			throw new OmieRepositoryException(e.getCause().getMessage(), e);
		}
	}
	
	protected PedidoDTO cadastrarPedido(PedidoDTO pedidoDTO, ClienteIntegracaoDTO clienteIntegracaoDTO) throws OmieRepositoryException {
		try {
			PedidoPayload pedidoOmie = new OmiePedidoBuilder().buildPedido(pedidoDTO, clienteIntegracaoDTO);
			OmieApiClient apiClient = new OmieApiClient();
			String jsonRequest = JsonUtils.toJson(pedidoOmie);
			System.out.print(jsonRequest);
			ApiClientResponse response = apiClient.post("/produtos/pedido/", "IncluirPedido", pedidoOmie);
			JSONObject json = JsonUtils.parse(response.getBody());
			BigInteger numeroPedido = new BigInteger(json.getString("numero_pedido"));
			pedidoDTO.setIdPedido(numeroPedido);
			return pedidoDTO;
		} catch (Exception e) {
			throw new OmieRepositoryException(e.getCause().getMessage(), e);
		}
	}
	
	public PedidoDTO alterarPedidoCliente(PedidoDTO pedidoDTO, ClienteIntegracaoDTO clienteIntegracaoDTO) throws OmieRepositoryException {
		try {
			return this.alterarPedido(pedidoDTO, clienteIntegracaoDTO);
		} catch (Exception e) {
			throw new OmieRepositoryException(String.format("Erro ao alterar pedido para cliente %d", pedidoDTO.getIdCliente()), e);
		}
	}
	
	protected PedidoDTO alterarPedido(PedidoDTO pedidoDTO, ClienteIntegracaoDTO clienteIntegracaoDTO) throws OmieRepositoryException {
		try {
			PedidoPayload pedidoOmie = new OmiePedidoBuilder().buildPedido(pedidoDTO, clienteIntegracaoDTO);
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/produtos/pedido/", "AlterarPedidoVenda", pedidoOmie);
			JSONObject json = JsonUtils.parse(response.getBody());
			BigInteger numeroPedido = new BigInteger(json.getString("numero_pedido"));
			pedidoDTO.setIdPedido(numeroPedido);
			return pedidoDTO;
		} catch (Exception e) {
			throw new OmieRepositoryException(String.format("Erro ao alterar pedido para cliente %d", pedidoDTO.getIdCliente()), e);
		}
	}
	
	public void inativarPedido(BigInteger codigoPedidoIntegracao, BigInteger codigoPedido) throws OmieRepositoryException {
		try {
			InativarPayloadPedido inativarPedidoOmie = new OmiePedidoBuilder().buildInativarPedido(codigoPedidoIntegracao, codigoPedido);
			OmieApiClient apiClient = new OmieApiClient();
			apiClient.post("/produtos/pedidovendafat/", "CancelarPedidoVenda", inativarPedidoOmie);
		} catch (Exception e) {
			throw new OmieRepositoryException(e);
		}
	}
}
