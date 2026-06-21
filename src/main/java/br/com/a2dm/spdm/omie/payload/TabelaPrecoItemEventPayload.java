package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TabelaPrecoItemEventPayload {

	private BigInteger nCodTabPreco;
	private BigInteger nCodProd;
	private Double nValorTabela;
	private Double nValorOriginal;
	private Double nValorCalculado;
	private Double nPercAcrescimo;
	private Double nPercDesconto;

	public TabelaPrecoItemEventPayload() {
		super();
	}

	public BigInteger getnCodTabPreco() {
		return nCodTabPreco;
	}

	public void setnCodTabPreco(BigInteger nCodTabPreco) {
		this.nCodTabPreco = nCodTabPreco;
	}

	public BigInteger getnCodProd() {
		return nCodProd;
	}

	public void setnCodProd(BigInteger nCodProd) {
		this.nCodProd = nCodProd;
	}

	public Double getnValorTabela() {
		return nValorTabela;
	}

	public void setnValorTabela(Double nValorTabela) {
		this.nValorTabela = nValorTabela;
	}

	public Double getnValorOriginal() {
		return nValorOriginal;
	}

	public void setnValorOriginal(Double nValorOriginal) {
		this.nValorOriginal = nValorOriginal;
	}

	public Double getnValorCalculado() {
		return nValorCalculado;
	}

	public void setnValorCalculado(Double nValorCalculado) {
		this.nValorCalculado = nValorCalculado;
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
}
