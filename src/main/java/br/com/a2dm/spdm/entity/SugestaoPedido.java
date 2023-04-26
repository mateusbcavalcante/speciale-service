package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_sugestao_pedido", schema="ped")
@SequenceGenerator(name = "SQ_SUGESTAO_PEDIDO", sequenceName = "SQ_SUGESTAO_PEDIDO", allocationSize = 1)
@Proxy(lazy = true)
public class SugestaoPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SUGESTAO_PEDIDO")
    @Column(name = "id_sugestao_pedido")
    private BigInteger idSugestaoPedido;
    
    @Column(name = "event_id")
    private BigInteger eventId;

    @Column(name = "store_cnpj")
    private String storeCNPJ;

    @Column(name = "event_code")
    private String eventCode;

    @Column(name = "event_title")
    private String eventTitle;
    
    @Column(name = "event_dth")
    private Date eventDth;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "agent_code")
    private String agentCode;

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "codigo_roteiro")
    private String codigoRoteiro;

    @Column(name = "link_rastreamento")
    private String linkRastreamento;
    
    @Column(name = "razao_social_dest")
    private String razaoSocialDest;
    
    @Column(name = "nome_fantasia_dest")
	private String nomeFantasiaDest;
    
    @Column(name = "codigo_destino")
	private BigInteger codigoDestino;

    @OneToMany(mappedBy="sugestaoPedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> itens;
    
    @Column(name = "status")
    private String status;		
    
    @Transient
	private String imagem;
    
    @Column(name = "id_opcao_entrega")
	private BigInteger idOpcaoEntrega;
    
    @Temporal(TemporalType.DATE)
	@Column(name = "dat_pedido")
	private Date datPedido;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_opcao_entrega", insertable = false, updatable = false)
	@JsonIgnore
	private OpcaoEntrega opcaoEntrega;
    
    public SugestaoPedido() {
    	super();
    }
    
	public SugestaoPedido(String imagem, List<Item> itens, String status) {
		this.imagem = imagem;
		this.itens = itens;
		this.status = status;
	}

	public BigInteger getIdSugestaoPedido() {
		return idSugestaoPedido;
	}

	public void setIdSugestaoPedido(BigInteger idSugestaoPedido) {
		this.idSugestaoPedido = idSugestaoPedido;
	}

	public BigInteger getEventId() {
		return eventId;
	}

	public void setEventId(BigInteger eventId) {
		this.eventId = eventId;
	}

	public String getStoreCNPJ() {
		return storeCNPJ;
	}

	public void setStoreCNPJ(String storeCNPJ) {
		this.storeCNPJ = storeCNPJ;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public Date getEventDth() {
		return eventDth;
	}

	public void setEventDth(Date eventDth) {
		this.eventDth = eventDth;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getCodigoRoteiro() {
		return codigoRoteiro;
	}

	public void setCodigoRoteiro(String codigoRoteiro) {
		this.codigoRoteiro = codigoRoteiro;
	}

	public String getLinkRastreamento() {
		return linkRastreamento;
	}

	public void setLinkRastreamento(String linkRastreamento) {
		this.linkRastreamento = linkRastreamento;
	}

	public String getRazaoSocialDest() {
		return razaoSocialDest;
	}

	public void setRazaoSocialDest(String razaoSocialDest) {
		this.razaoSocialDest = razaoSocialDest;
	}

	public String getNomeFantasiaDest() {
		return nomeFantasiaDest;
	}

	public void setNomeFantasiaDest(String nomeFantasiaDest) {
		this.nomeFantasiaDest = nomeFantasiaDest;
	}

	public BigInteger getCodigoDestino() {
		return codigoDestino;
	}

	public void setCodigoDestino(BigInteger codigoDestino) {
		this.codigoDestino = codigoDestino;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public Date getDatPedido() {
		return datPedido;
	}

	public void setDatPedido(Date datPedido) {
		this.datPedido = datPedido;
	}

	public BigInteger getIdOpcaoEntrega() {
		return idOpcaoEntrega;
	}

	public void setIdOpcaoEntrega(BigInteger idOpcaoEntrega) {
		this.idOpcaoEntrega = idOpcaoEntrega;
	}

	public OpcaoEntrega getOpcaoEntrega() {
		return opcaoEntrega;
	}

	public void setOpcaoEntrega(OpcaoEntrega opcaoEntrega) {
		this.opcaoEntrega = opcaoEntrega;
	}
	
}