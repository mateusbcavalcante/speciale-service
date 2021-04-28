package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;

public class AlterarPayloadCliente {

	private BigInteger codigo_cliente_omie;
	private String nome_fantasia;
	
	public AlterarPayloadCliente() {
		super();
	}

	public AlterarPayloadCliente(BigInteger codigo_cliente_omie, String nome_fantasia) {
		super();
		this.codigo_cliente_omie = codigo_cliente_omie;
		this.nome_fantasia = nome_fantasia;
	}

	public BigInteger getCodigo_cliente_omie() {
		return codigo_cliente_omie;
	}

	public void setCodigo_cliente_omie(BigInteger codigo_cliente_omie) {
		this.codigo_cliente_omie = codigo_cliente_omie;
	}

	public String getNome_fantasia() {
		return nome_fantasia;
	}

	public void setNome_fantasia(String nome_fantasia) {
		this.nome_fantasia = nome_fantasia;
	}

	@Override
	public String toString() {
		return "AlterarPayloadCliente [codigo_cliente_omie=" + codigo_cliente_omie + ", nome_fantasia=" + nome_fantasia
				+ "]";
	}
}
