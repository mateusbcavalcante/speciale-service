package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;

public class TabelaPrecoPayload {

	private BigInteger nCodTabPreco;
	private String cCodigo;
	private String cNome;
	private String cOrigem;
	private String cAtiva;
	private Double nPercAcrescimo;
	private Double nPercDesconto;

	public TabelaPrecoPayload() {
		super();
	}

	public TabelaPrecoPayload(BigInteger nCodTabPreco, String cNome) {
		super();
		this.nCodTabPreco = nCodTabPreco;
		this.cNome = cNome;
	}

	public BigInteger getnCodTabPreco() {
		return nCodTabPreco;
	}

	public void setnCodTabPreco(BigInteger nCodTabPreco) {
		this.nCodTabPreco = nCodTabPreco;
	}

	public String getcCodigo() {
		return cCodigo;
	}

	public void setcCodigo(String cCodigo) {
		this.cCodigo = cCodigo;
	}

	public String getcNome() {
		return cNome;
	}

	public void setcNome(String cNome) {
		this.cNome = cNome;
	}

	public String getcOrigem() {
		return cOrigem;
	}

	public void setcOrigem(String cOrigem) {
		this.cOrigem = cOrigem;
	}

	public String getcAtiva() {
		return cAtiva;
	}

	public void setcAtiva(String cAtiva) {
		this.cAtiva = cAtiva;
	}

	public Double getnPercAcrescimo() {
		return nPercAcrescimo;
	}

	public void setnPercAcrescimo(Double nPercAcrescimo) {
		this.nPercAcrescimo = nPercAcrescimo;
	}

	public Double getnPercDesconto() {
		return nPercDesconto;
	}

	public void setnPercDesconto(Double nPercDesconto) {
		this.nPercDesconto = nPercDesconto;
	}

	public boolean isInativa() {
		return "N".equalsIgnoreCase(cAtiva);
	}

	@Override
	public String toString() {
		return "TabelaPrecoPayload [nCodTabPreco=" + nCodTabPreco + ", cCodigo=" + cCodigo + ", cNome=" + cNome
				+ ", cOrigem=" + cOrigem + ", cAtiva=" + cAtiva + ", nPercAcrescimo=" + nPercAcrescimo
				+ ", nPercDesconto=" + nPercDesconto + "]";
	}
}
