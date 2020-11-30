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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Proxy;

import br.com.a2dm.brcmn.entity.Usuario;

/** 
 * @author Carlos Diego
 * @since 26/01/2016
 */

@Entity
@Table(name = "tb_cliente", schema="ped")
@SequenceGenerator(name = "SQ_CLIENTE", sequenceName = "SQ_CLIENTE", allocationSize = 1)
@Proxy(lazy = true)
@FilterDefs
({
	@FilterDef(name = "filtroClienteProdutoAtivo", parameters = @ParamDef(name = "flagAtivoClienteProduto", type = "string")),    
})
public class Cliente implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CLIENTE")
	@Column(name = "id_cliente")
	private BigInteger idCliente;
	
	@Column(name = "des_cliente")
	private String desCliente;
	
	@Column(name = "hor_limite")
	private String horLimite;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_cadastro")
	private Date datCadastro;
	
	@Column(name = "id_usuario_cad")
	private BigInteger idUsuarioCad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_cad", insertable = false, updatable = false)
	private Usuario usuarioCad;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_alteracao")
	private Date datAlteracao;
	
	@Column(name = "id_usuario_alt")
	private BigInteger idUsuarioAlt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_alt", insertable = false, updatable = false)
	private Usuario usuarioAlt;
	
	@Column(name = "flg_ativo")
	private String flgAtivo;
	
	@Column(name = "num_prioridade")
	private BigInteger numPrioridade;
	
	@OneToMany(mappedBy="cliente", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
	@Filter(name = "filtroClienteProdutoAtivo", condition = ":flagAtivoClienteProduto = flg_ativo")
	private List<ClienteProduto> listaClienteProduto;
	
	@Column(name = "vlr_frete")
	private Double vlrFrete;
	
	@Column(name = "id_forma_pagamento")
	private BigInteger idFormaPagamento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_forma_pagamento", insertable = false, updatable = false)
	private FormaPagamento formaPagamento;
	
	@Column(name = "id_tipo")
	private BigInteger idTipo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo", insertable = false, updatable = false)
	private Tipo tipo;
	
	@Column(name = "id_externo")
	private BigInteger idExterno;
	
	@Column(name = "id_tabela_preco_omie")
	private BigInteger idTabelaPrecoOmie;
	
	@Column(name = "flg_sinc")
	private String flgSinc;
	
	@Column(name = "flg_evento")
	private String flgEvento;
	
	@Column(name = "flg_master")
	private String flgMaster;
	
	@Transient
	private List<Produto> listaProduto;
	
	@Transient
	private String vlrFreteFormatado;
	
	@Transient
	private HashMap<String, Object> filtroMap;

	public BigInteger getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(BigInteger idCliente) {
		this.idCliente = idCliente;
	}

	public String getDesCliente() {
		return desCliente;
	}

	public void setDesCliente(String desCliente) {
		this.desCliente = desCliente;
	}

	public String getHorLimite() {
		return horLimite;
	}

	public void setHorLimite(String horLimite) {
		this.horLimite = horLimite;
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

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public List<ClienteProduto> getListaClienteProduto() {
		return listaClienteProduto;
	}

	public void setListaClienteProduto(List<ClienteProduto> listaClienteProduto) {
		this.listaClienteProduto = listaClienteProduto;
	}

	public BigInteger getNumPrioridade() {
		return numPrioridade;
	}

	public void setNumPrioridade(BigInteger numPrioridade) {
		this.numPrioridade = numPrioridade;
	}

	public Double getVlrFrete() {
		return vlrFrete;
	}

	public void setVlrFrete(Double vlrFrete) {
		this.vlrFrete = vlrFrete;
	}

	public BigInteger getIdFormaPagamento() {
		return idFormaPagamento;
	}

	public void setIdFormaPagamento(BigInteger idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public BigInteger getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(BigInteger idTipo) {
		this.idTipo = idTipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getVlrFreteFormatado() {
		return vlrFreteFormatado;
	}

	public void setVlrFreteFormatado(String vlrFreteFormatado) {
		this.vlrFreteFormatado = vlrFreteFormatado;
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
	
	public String getFlgEvento() {
		return flgEvento;
	}

	public void setFlgEvento(String flgEvento) {
		this.flgEvento = flgEvento;
	}
	
	public String getFlgMaster() {
		return flgMaster;
	}

	public void setFlgMaster(String flgMaster) {
		this.flgMaster = flgMaster;
	}

	public BigInteger getIdTabelaPrecoOmie() {
		return idTabelaPrecoOmie;
	}

	public void setIdTabelaPrecoOmie(BigInteger idTabelaPrecoOmie) {
		this.idTabelaPrecoOmie = idTabelaPrecoOmie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datAlteracao == null) ? 0 : datAlteracao.hashCode());
		result = prime * result + ((datCadastro == null) ? 0 : datCadastro.hashCode());
		result = prime * result + ((desCliente == null) ? 0 : desCliente.hashCode());
		result = prime * result + ((filtroMap == null) ? 0 : filtroMap.hashCode());
		result = prime * result + ((flgAtivo == null) ? 0 : flgAtivo.hashCode());
		result = prime * result + ((flgSinc == null) ? 0 : flgSinc.hashCode());
		result = prime * result + ((formaPagamento == null) ? 0 : formaPagamento.hashCode());
		result = prime * result + ((horLimite == null) ? 0 : horLimite.hashCode());
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		result = prime * result + ((idExterno == null) ? 0 : idExterno.hashCode());
		result = prime * result + ((idFormaPagamento == null) ? 0 : idFormaPagamento.hashCode());
		result = prime * result + ((idTipo == null) ? 0 : idTipo.hashCode());
		result = prime * result + ((idUsuarioAlt == null) ? 0 : idUsuarioAlt.hashCode());
		result = prime * result + ((idUsuarioCad == null) ? 0 : idUsuarioCad.hashCode());
		result = prime * result + ((listaClienteProduto == null) ? 0 : listaClienteProduto.hashCode());
		result = prime * result + ((listaProduto == null) ? 0 : listaProduto.hashCode());
		result = prime * result + ((numPrioridade == null) ? 0 : numPrioridade.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((usuarioAlt == null) ? 0 : usuarioAlt.hashCode());
		result = prime * result + ((usuarioCad == null) ? 0 : usuarioCad.hashCode());
		result = prime * result + ((vlrFrete == null) ? 0 : vlrFrete.hashCode());
		result = prime * result + ((vlrFreteFormatado == null) ? 0 : vlrFreteFormatado.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (datAlteracao == null) {
			if (other.datAlteracao != null)
				return false;
		} else if (!datAlteracao.equals(other.datAlteracao))
			return false;
		if (datCadastro == null) {
			if (other.datCadastro != null)
				return false;
		} else if (!datCadastro.equals(other.datCadastro))
			return false;
		if (desCliente == null) {
			if (other.desCliente != null)
				return false;
		} else if (!desCliente.equals(other.desCliente))
			return false;
		if (filtroMap == null) {
			if (other.filtroMap != null)
				return false;
		} else if (!filtroMap.equals(other.filtroMap))
			return false;
		if (flgAtivo == null) {
			if (other.flgAtivo != null)
				return false;
		} else if (!flgAtivo.equals(other.flgAtivo))
			return false;
		if (flgSinc == null) {
			if (other.flgSinc != null)
				return false;
		} else if (!flgSinc.equals(other.flgSinc))
			return false;
		if (formaPagamento == null) {
			if (other.formaPagamento != null)
				return false;
		} else if (!formaPagamento.equals(other.formaPagamento))
			return false;
		if (horLimite == null) {
			if (other.horLimite != null)
				return false;
		} else if (!horLimite.equals(other.horLimite))
			return false;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		if (idExterno == null) {
			if (other.idExterno != null)
				return false;
		} else if (!idExterno.equals(other.idExterno))
			return false;
		if (idFormaPagamento == null) {
			if (other.idFormaPagamento != null)
				return false;
		} else if (!idFormaPagamento.equals(other.idFormaPagamento))
			return false;
		if (idTipo == null) {
			if (other.idTipo != null)
				return false;
		} else if (!idTipo.equals(other.idTipo))
			return false;
		if (idUsuarioAlt == null) {
			if (other.idUsuarioAlt != null)
				return false;
		} else if (!idUsuarioAlt.equals(other.idUsuarioAlt))
			return false;
		if (idUsuarioCad == null) {
			if (other.idUsuarioCad != null)
				return false;
		} else if (!idUsuarioCad.equals(other.idUsuarioCad))
			return false;
		if (listaClienteProduto == null) {
			if (other.listaClienteProduto != null)
				return false;
		} else if (!listaClienteProduto.equals(other.listaClienteProduto))
			return false;
		if (listaProduto == null) {
			if (other.listaProduto != null)
				return false;
		} else if (!listaProduto.equals(other.listaProduto))
			return false;
		if (numPrioridade == null) {
			if (other.numPrioridade != null)
				return false;
		} else if (!numPrioridade.equals(other.numPrioridade))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (usuarioAlt == null) {
			if (other.usuarioAlt != null)
				return false;
		} else if (!usuarioAlt.equals(other.usuarioAlt))
			return false;
		if (usuarioCad == null) {
			if (other.usuarioCad != null)
				return false;
		} else if (!usuarioCad.equals(other.usuarioCad))
			return false;
		if (vlrFrete == null) {
			if (other.vlrFrete != null)
				return false;
		} else if (!vlrFrete.equals(other.vlrFrete))
			return false;
		if (vlrFreteFormatado == null) {
			if (other.vlrFreteFormatado != null)
				return false;
		} else if (!vlrFreteFormatado.equals(other.vlrFreteFormatado))
			return false;
		return true;
	}
}
