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
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "tb_ativmob_event", schema="ped")
@SequenceGenerator(name = "SQ_EVENT", sequenceName = "SQ_EVENT", allocationSize = 1)
@Proxy(lazy = true)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EVENT")
    @Column(name = "id_event")
    private BigInteger id_event;
    
    @Column(name = "event_id")
    private BigInteger event_id;

    @Column(name = "store_cnpj")
    private String storeCNPJ;

    @Column(name = "event_code")
    private String event_code;

    @Column(name = "event_title")
    private String event_title;

    @Column(name = "event_dth")
    private Date event_dth;

    @Column(name = "order_number")
    private String order_number;

    @Column(name = "invoice_number")
    private String invoice_number;

    @Column(name = "agent_code")
    private String agent_code;

    @Column(name = "agent_name")
    private String agent_name;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "codigo_roteiro")
    private String codigo_roteiro;

    @Column(name = "link_rastreamento")
    private String link_rastreamento;

    @OneToMany(mappedBy="event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Form> forms;
    
    @Column(name = "status")
    private String status;		
    
    @Transient
	private String imagem;
    
    @Column(name = "id_cliente")
	private BigInteger idCliente;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", insertable = false, updatable = false)
	private Cliente cliente;
    
    public Event() {
    	super();
    }
    
	public Event(String imagem, List<Form> forms) {
		this.imagem = imagem;
		this.forms = forms;
	}

	public BigInteger getId_event() {
		return id_event;
	}

	public void setId_event(BigInteger id_event) {
		this.id_event = id_event;
	}

	public BigInteger getEvent_id() {
		return event_id;
	}

	public void setEvent_id(BigInteger event_id) {
		this.event_id = event_id;
	}

	public String getStoreCNPJ() {
		return storeCNPJ;
	}

	public void setStoreCNPJ(String storeCNPJ) {
		this.storeCNPJ = storeCNPJ;
	}

	public String getEvent_code() {
		return event_code;
	}

	public void setEvent_code(String event_code) {
		this.event_code = event_code;
	}

	public String getEvent_title() {
		return event_title;
	}

	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}

	public Date getEvent_dth() {
		return event_dth;
	}

	public void setEvent_dth(Date event_dth) {
		this.event_dth = event_dth;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getAgent_code() {
		return agent_code;
	}

	public void setAgent_code(String agent_code) {
		this.agent_code = agent_code;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
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

	public String getCodigo_roteiro() {
		return codigo_roteiro;
	}

	public void setCodigo_roteiro(String codigo_roteiro) {
		this.codigo_roteiro = codigo_roteiro;
	}

	public String getLink_rastreamento() {
		return link_rastreamento;
	}

	public void setLink_rastreamento(String link_rastreamento) {
		this.link_rastreamento = link_rastreamento;
	}

	public List<Form> getForms() {
		return forms;
	}

	public void setForms(List<Form> forms) {
		this.forms = forms;
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
}