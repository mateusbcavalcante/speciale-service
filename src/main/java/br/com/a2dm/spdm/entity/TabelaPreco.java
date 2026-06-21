package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_tabela_preco", schema = "ped")
@SequenceGenerator(name = "SQ_TABELA_PRECO", sequenceName = "SQ_TABELA_PRECO", allocationSize = 1)
@Proxy(lazy = true)
public class TabelaPreco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TABELA_PRECO")
	@Column(name = "id_tabela_preco")
	private BigInteger idTabelaPreco;

	@Column(name = "id_externo")
	private BigInteger idExterno;

	@Column(name = "cod_tabela")
	private String codTabela;

	@Column(name = "nom_tabela")
	private String nomTabela;

	@Column(name = "des_origem")
	private String desOrigem;

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

	@OneToMany(mappedBy = "tabelaPreco", fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	@JsonIgnore
	private List<TabelaPrecoItem> listaTabelaPrecoItem;

	@Transient
	private HashMap<String, Object> filtroMap;

	public BigInteger getIdTabelaPreco() {
		return idTabelaPreco;
	}

	public void setIdTabelaPreco(BigInteger idTabelaPreco) {
		this.idTabelaPreco = idTabelaPreco;
	}

	public BigInteger getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(BigInteger idExterno) {
		this.idExterno = idExterno;
	}

	public String getCodTabela() {
		return codTabela;
	}

	public void setCodTabela(String codTabela) {
		this.codTabela = codTabela;
	}

	public String getNomTabela() {
		return nomTabela;
	}

	public void setNomTabela(String nomTabela) {
		this.nomTabela = nomTabela;
	}

	public String getDesOrigem() {
		return desOrigem;
	}

	public void setDesOrigem(String desOrigem) {
		this.desOrigem = desOrigem;
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

	public List<TabelaPrecoItem> getListaTabelaPrecoItem() {
		return listaTabelaPrecoItem;
	}

	public void setListaTabelaPrecoItem(List<TabelaPrecoItem> listaTabelaPrecoItem) {
		this.listaTabelaPrecoItem = listaTabelaPrecoItem;
	}

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}
}
