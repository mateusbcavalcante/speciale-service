package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TabelaPrecoEventPayload {

	private BigInteger nCodTabPreco;
	private String cCodigo;
	private String cNome;
	private String cOrigem;
	private String cAtiva;
	private TabelaPrecoOutrasInfoPayload outrasInfo;

	public TabelaPrecoEventPayload() {
		super();
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

	public TabelaPrecoOutrasInfoPayload getOutrasInfo() {
		return outrasInfo;
	}

	public void setOutrasInfo(TabelaPrecoOutrasInfoPayload outrasInfo) {
		this.outrasInfo = outrasInfo;
	}
}
