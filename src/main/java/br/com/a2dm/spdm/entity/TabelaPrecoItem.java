package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_tabela_preco_item", schema = "ped")
@SequenceGenerator(name = "SQ_TABELA_PRECO_ITEM", sequenceName = "SQ_TABELA_PRECO_ITEM", allocationSize = 1)
@Proxy(lazy = true)
public class TabelaPrecoItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TABELA_PRECO_ITEM")
	@Column(name = "id_tabela_preco_item")
	private BigInteger idTabelaPrecoItem;

	@Column(name = "id_tabela_preco")
	private BigInteger idTabelaPreco;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tabela_preco", insertable = false, updatable = false)
	@JsonIgnore
	private TabelaPreco tabelaPreco;

	@Column(name = "id_produto")
	private BigInteger idProduto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto", insertable = false, updatable = false)
	@JsonIgnore
	private Produto produto;

	@Column(name = "id_externo_tabela")
	private BigInteger idExternoTabela;

	@Column(name = "id_externo_produto")
	private BigInteger idExternoProduto;

	@Column(name = "vlr_tabela")
	private Double vlrTabela;

	@Column(name = "vlr_original")
	private Double vlrOriginal;

	@Column(name = "vlr_calculado")
	private Double vlrCalculado;

	@Column(name = "per_acrescimo")
	private Double perAcrescimo;

	@Column(name = "per_desconto")
	private Double perDesconto;

	@Column(name = "flg_ativo")
	private String flgAtivo;

	@Column(name = "flg_sinc")
	private String flgSinc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_cadastro")
	private Date datCadastro;

	@Column(name = "id_usuario_cad")
	private BigInteger idUsuarioCad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_alteracao")
	private Date datAlteracao;

	@Column(name = "id_usuario_alt")
	private BigInteger idUsuarioAlt;

	@Transient
	private HashMap<String, Object> filtroMap;

	public BigInteger getIdTabelaPrecoItem() {
		return idTabelaPrecoItem;
	}

	public void setIdTabelaPrecoItem(BigInteger idTabelaPrecoItem) {
		this.idTabelaPrecoItem = idTabelaPrecoItem;
	}

	public BigInteger getIdTabelaPreco() {
		return idTabelaPreco;
	}

	public void setIdTabelaPreco(BigInteger idTabelaPreco) {
		this.idTabelaPreco = idTabelaPreco;
	}

	public TabelaPreco getTabelaPreco() {
		return tabelaPreco;
	}

	public void setTabelaPreco(TabelaPreco tabelaPreco) {
		this.tabelaPreco = tabelaPreco;
	}

	public BigInteger getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(BigInteger idProduto) {
		this.idProduto = idProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigInteger getIdExternoTabela() {
		return idExternoTabela;
	}

	public void setIdExternoTabela(BigInteger idExternoTabela) {
		this.idExternoTabela = idExternoTabela;
	}

	public BigInteger getIdExternoProduto() {
		return idExternoProduto;
	}

	public void setIdExternoProduto(BigInteger idExternoProduto) {
		this.idExternoProduto = idExternoProduto;
	}

	public Double getVlrTabela() {
		return vlrTabela;
	}

	public void setVlrTabela(Double vlrTabela) {
		this.vlrTabela = vlrTabela;
	}

	public Double getVlrOriginal() {
		return vlrOriginal;
	}

	public void setVlrOriginal(Double vlrOriginal) {
		this.vlrOriginal = vlrOriginal;
	}

	public Double getVlrCalculado() {
		return vlrCalculado;
	}

	public void setVlrCalculado(Double vlrCalculado) {
		this.vlrCalculado = vlrCalculado;
	}

	public Double getPerAcrescimo() {
		return perAcrescimo;
	}

	public void setPerAcrescimo(Double perAcrescimo) {
		this.perAcrescimo = perAcrescimo;
	}

	public Double getPerDesconto() {
		return perDesconto;
	}

	public void setPerDesconto(Double perDesconto) {
		this.perDesconto = perDesconto;
	}

	public String getFlgAtivo() {
		return flgAtivo;
	}

	public void setFlgAtivo(String flgAtivo) {
		this.flgAtivo = flgAtivo;
	}

	public String getFlgSinc() {
		return flgSinc;
	}

	public void setFlgSinc(String flgSinc) {
		this.flgSinc = flgSinc;
	}

	public Date getDatCadastro() {
		return datCadastro;
	}

	public void setDatCadastro(Date datCadastro) {
		this.datCadastro = datCadastro;
	}

	public BigInteger getIdUsuarioCad() {
		return idUsuarioCad;
	}

	public void setIdUsuarioCad(BigInteger idUsuarioCad) {
		this.idUsuarioCad = idUsuarioCad;
	}

	public Date getDatAlteracao() {
		return datAlteracao;
	}

	public void setDatAlteracao(Date datAlteracao) {
		this.datAlteracao = datAlteracao;
	}

	public BigInteger getIdUsuarioAlt() {
		return idUsuarioAlt;
	}

	public void setIdUsuarioAlt(BigInteger idUsuarioAlt) {
		this.idUsuarioAlt = idUsuarioAlt;
	}

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}
}
