package br.com.a2dm.spdm.omie.payload;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TabelaPrecoOutrasInfoPayload {

	private Double nPercAcrescimo;
	private Double nPercDesconto;

	public TabelaPrecoOutrasInfoPayload() {
		super();
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
