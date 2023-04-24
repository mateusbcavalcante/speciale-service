package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;

public class ProdutoEstruturaPayload {
	
	private BigInteger idProduto;

	public ProdutoEstruturaPayload(BigInteger idProduto) {
		super();
		this.idProduto = idProduto;
	}

	public BigInteger getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(BigInteger idProduto) {
		this.idProduto = idProduto;
	}
	
}
