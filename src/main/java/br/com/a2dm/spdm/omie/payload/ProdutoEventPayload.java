package br.com.a2dm.spdm.omie.payload;

import java.math.BigInteger;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoEventPayload {

	private BigInteger codigo_produto;
	private String codigo;
	private String descricao;
	private String unidade;
	private String inativo;
	private BigInteger codigo_familia;
	private String descricao_familia;
	private Double valor_unitario;

	public ProdutoEventPayload() {
		super();
	}

	public BigInteger getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(BigInteger codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getInativo() {
		return inativo;
	}

	public void setInativo(String inativo) {
		this.inativo = inativo;
	}

	public BigInteger getCodigo_familia() {
		return codigo_familia;
	}

	public void setCodigo_familia(BigInteger codigo_familia) {
		this.codigo_familia = codigo_familia;
	}

	public String getDescricao_familia() {
		return descricao_familia;
	}

	public void setDescricao_familia(String descricao_familia) {
		this.descricao_familia = descricao_familia;
	}

	public Double getValor_unitario() {
		return valor_unitario;
	}

	public void setValor_unitario(Double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}
}
