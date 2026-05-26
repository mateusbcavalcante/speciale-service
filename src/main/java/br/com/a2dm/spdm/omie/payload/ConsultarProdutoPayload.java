package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;

public class ConsultarProdutoPayload {

	private BigInteger codigo_produto;

	public ConsultarProdutoPayload(BigInteger codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	public BigInteger getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(BigInteger codigo_produto) {
		this.codigo_produto = codigo_produto;
	}
}
