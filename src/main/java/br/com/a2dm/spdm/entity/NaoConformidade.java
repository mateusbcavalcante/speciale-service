package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_nao_conformidade", schema="ped")
@SequenceGenerator(name = "SQ_NAO_CONFORMIDADE", sequenceName = "SQ_NAO_CONFORMIDADE", allocationSize = 1)
@Proxy(lazy = true)
public class NaoConformidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_NAO_CONFORMIDADE")
    @Column(name = "id_nao_conformidade")
    private BigInteger idNaoConformidade;
    
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_cadastro")
	private Date datCadastro;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_alteracao")
	private Date datAlteracao;
    
    @Column(name = "id_cliente")
	private BigInteger idCliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", insertable = false, updatable = false)
	@JsonIgnore
	private Cliente cliente;

    @Column(name = "id_produto")
    private BigInteger idProduto;
    
    @Column(name = "descricao_produto")
    private String descricaoProduto;

    @Column(name = "quantidade")
    private BigInteger quantidade;

    @Column(name = "setor")
    private String setor;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "observacao")
    private String observacao;
    
    @Column(name = "lote")
    private BigInteger lote;
    
    @Column(name = "ativo")
    private boolean ativo;

	public BigInteger getIdNaoConformidade() {
		return idNaoConformidade;
	}

	public void setIdNaoConformidade(BigInteger idNaoConformidade) {
		this.idNaoConformidade = idNaoConformidade;
	}

	public Date getDatCadastro() {
		return datCadastro;
	}

	public void setDatCadastro(Date datCadastro) {
		this.datCadastro = datCadastro;
	}

	public Date getDatAlteracao() {
		return datAlteracao;
	}

	public void setDatAlteracao(Date datAlteracao) {
		this.datAlteracao = datAlteracao;
	}

	public BigInteger getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(BigInteger idCliente) {
		this.idCliente = idCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigInteger getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(BigInteger idProduto) {
		this.idProduto = idProduto;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public BigInteger getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigInteger quantidade) {
		this.quantidade = quantidade;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigInteger getLote() {
		return lote;
	}

	public void setLote(BigInteger lote) {
		this.lote = lote;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}