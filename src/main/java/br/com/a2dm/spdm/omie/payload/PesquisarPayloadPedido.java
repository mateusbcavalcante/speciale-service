package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;

public class PesquisarPayloadPedido {

	private BigInteger filtrar_por_cliente;
	private BigInteger numero_pedido_de;
	private BigInteger numero_pedido_ate;
	private String data_previsao_de;
	private String data_previsao_ate;
	
	public PesquisarPayloadPedido(BigInteger filtrar_por_cliente, String dataPedido) {
		super();
		this.filtrar_por_cliente = filtrar_por_cliente;
		this.data_previsao_de = dataPedido;
		this.data_previsao_ate = dataPedido;
	}

	public PesquisarPayloadPedido(BigInteger filtrar_por_cliente, BigInteger numeroPedido, String dataPedido) {
		super();
		this.filtrar_por_cliente = filtrar_por_cliente;
		this.numero_pedido_de = numeroPedido;
		this.numero_pedido_ate = numeroPedido;
		this.data_previsao_de = dataPedido;
		this.data_previsao_ate = dataPedido;
	}

	public BigInteger getFiltrar_por_cliente() {
		return filtrar_por_cliente;
	}

	public void setFiltrar_por_cliente(BigInteger filtrar_por_cliente) {
		this.filtrar_por_cliente = filtrar_por_cliente;
	}

	public BigInteger getNumero_pedido_de() {
		return numero_pedido_de;
	}

	public void setNumero_pedido_de(BigInteger numero_pedido_de) {
		this.numero_pedido_de = numero_pedido_de;
	}

	public BigInteger getNumero_pedido_ate() {
		return numero_pedido_ate;
	}

	public void setNumero_pedido_ate(BigInteger numero_pedido_ate) {
		this.numero_pedido_ate = numero_pedido_ate;
	}

	public String getData_previsao_de() {
		return data_previsao_de;
	}

	public void setData_previsao_de(String data_previsao_de) {
		this.data_previsao_de = data_previsao_de;
	}

	public String getData_previsao_ate() {
		return data_previsao_ate;
	}

	public void setData_previsao_ate(String data_previsao_ate) {
		this.data_previsao_ate = data_previsao_ate;
	}
}
