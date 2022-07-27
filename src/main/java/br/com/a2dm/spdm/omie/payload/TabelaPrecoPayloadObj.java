package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;
import java.util.List;

public class TabelaPrecoPayloadObj {

	private BigInteger nTotRegistros;
	private List<TabelaPrecoPayload> listaTabelasPreco;

	public TabelaPrecoPayloadObj() {
		super();
	}

	public TabelaPrecoPayloadObj(BigInteger nTotRegistros, List<TabelaPrecoPayload> listaTabelasPreco) {
		super();
		this.nTotRegistros = nTotRegistros;
		this.listaTabelasPreco = listaTabelasPreco;
	}

	public BigInteger getnTotRegistros() {
		return nTotRegistros;
	}

	public void setnTotRegistros(BigInteger nTotRegistros) {
		this.nTotRegistros = nTotRegistros;
	}

	public List<TabelaPrecoPayload> getListaTabelasPreco() {
		return listaTabelasPreco;
	}

	public void setListaTabelasPreco(List<TabelaPrecoPayload> listaTabelasPreco) {
		this.listaTabelasPreco = listaTabelasPreco;
	}

	@Override
	public String toString() {
		return "TabelaPrecoPayloadObj [nTotRegistros=" + nTotRegistros + ", listaTabelasPreco=" + listaTabelasPreco
				+ "]";
	}
}
