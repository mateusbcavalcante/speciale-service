package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;

public class PesquisarPayloadClienteIntegracao {

	private BigInteger codigo_cliente_omie;

	public PesquisarPayloadClienteIntegracao(BigInteger codigo_cliente_omie) {
		super();
		this.codigo_cliente_omie = codigo_cliente_omie;
	}

	public BigInteger getCodigo_cliente_omie() {
		return codigo_cliente_omie;
	}

	public void setCodigo_cliente_omie(BigInteger codigo_cliente_omie) {
		this.codigo_cliente_omie = codigo_cliente_omie;
	}
}
