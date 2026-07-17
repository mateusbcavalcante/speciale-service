package br.com.a2dm.spdm.omie.payload;

public class FretePayload {

	private String modalidade;
	private Integer quantidade_volumes;
	private String especie_volumes;

	public FretePayload(String modalidade) {
		super();
		this.modalidade = modalidade;
	}

	public FretePayload(String modalidade, Integer quantidade_volumes, String especie_volumes) {
		super();
		this.modalidade = modalidade;
		this.quantidade_volumes = quantidade_volumes;
		this.especie_volumes = especie_volumes;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getQuantidade_volumes() {
		return quantidade_volumes;
	}

	public void setQuantidade_volumes(Integer quantidade_volumes) {
		this.quantidade_volumes = quantidade_volumes;
	}

	public String getEspecie_volumes() {
		return especie_volumes;
	}

	public void setEspecie_volumes(String especie_volumes) {
		this.especie_volumes = especie_volumes;
	}
}
