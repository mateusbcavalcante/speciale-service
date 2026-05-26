package br.com.a2dm.spdm.omie.payload;

public class InfAdicPayload {

	private String nao_gerar_financeiro;

	public InfAdicPayload(String nao_gerar_financeiro) {
		this.nao_gerar_financeiro = nao_gerar_financeiro;
	}

	public String getNao_gerar_financeiro() {
		return nao_gerar_financeiro;
	}

	public void setNao_gerar_financeiro(String nao_gerar_financeiro) {
		this.nao_gerar_financeiro = nao_gerar_financeiro;
	}
}
