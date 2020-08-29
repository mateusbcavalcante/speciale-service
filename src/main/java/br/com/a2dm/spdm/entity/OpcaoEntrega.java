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

import br.com.a2dm.brcmn.entity.Usuario;

/**
 * @author Mateus Bastos
 * @since 03/06/2019
 */

@Entity
@Table(name = "tb_opcao_entrega", schema = "ped")
@SequenceGenerator(name = "SQ_OPCAO_ENTREGA", sequenceName = "SQ_OPCAO_ENTREGA", allocationSize = 1)
@Proxy(lazy = true)
public class OpcaoEntrega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_OPCAO_ENTREGA")
	@Column(name = "id_opcao_entrega")
	private BigInteger idOpcaoEntrega;

	@Column(name = "des_opcao_entrega")
	private String desOpcaoEntrega;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_cadastro")
	private Date datCadastro;

	@Column(name = "id_usuario_cad")
	private BigInteger idUsuarioCad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_cad", insertable = false, updatable = false)
	@JsonIgnore
	private Usuario usuarioCad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_alteracao")
	private Date datAlteracao;

	@Column(name = "id_usuario_alt")
	private BigInteger idUsuarioAlt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_alt", insertable = false, updatable = false)
	@JsonIgnore
	private Usuario usuarioAlt;

	@Column(name = "flg_ativo")
	private String flgAtivo;

	@Column(name = "id_externo")
	private BigInteger idExterno;

	@Column(name = "flg_sinc")
	private String flgSinc;

	@Transient
	private HashMap<String, Object> filtroMap;

	public BigInteger getIdOpcaoEntrega() {
		return idOpcaoEntrega;
	}

	public void setIdOpcaoEntrega(BigInteger idOpcaoEntrega) {
		this.idOpcaoEntrega = idOpcaoEntrega;
	}

	public String getDesOpcaoEntrega() {
		return desOpcaoEntrega;
	}

	public void setDesOpcaoEntrega(String desOpcaoEntrega) {
		this.desOpcaoEntrega = desOpcaoEntrega;
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

	public Usuario getUsuarioCad() {
		return usuarioCad;
	}

	public void setUsuarioCad(Usuario usuarioCad) {
		this.usuarioCad = usuarioCad;
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

	public Usuario getUsuarioAlt() {
		return usuarioAlt;
	}

	public void setUsuarioAlt(Usuario usuarioAlt) {
		this.usuarioAlt = usuarioAlt;
	}

	public String getFlgAtivo() {
		return flgAtivo;
	}

	public void setFlgAtivo(String flgAtivo) {
		this.flgAtivo = flgAtivo;
	}

	public BigInteger getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(BigInteger idExterno) {
		this.idExterno = idExterno;
	}

	public String getFlgSinc() {
		return flgSinc;
	}

	public void setFlgSinc(String flgSinc) {
		this.flgSinc = flgSinc;
	}

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}
}
