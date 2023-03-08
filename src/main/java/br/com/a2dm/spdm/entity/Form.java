package br.com.a2dm.spdm.entity;

import java.math.BigInteger;

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

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_ativmob_form", schema="ped")
@SequenceGenerator(name = "SQ_FORM", sequenceName = "SQ_FORM", allocationSize = 1)
@Proxy(lazy = true)
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FORM")
    @Column(name = "id_form")
    private BigInteger idForm;

    @Column(name = "type")
    private String type;

    @Column(name = "label")
    private String label;

    @Column(name = "url")
    private String url;

    @Column(name = "value")
    private Integer value;
    
    @Column(name = "id_event")
	private BigInteger idEvent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_event", insertable = false, updatable = false)
	private Event event;
    
    public Form() {
		super();
	}

	public Form(String type, String label, String url, Integer value, BigInteger idEvent,
			Event event) {
		super();
		this.type = type;
		this.label = label;
		this.url = url;
		this.value = value;
		this.idEvent = idEvent;
		this.event = event;
	}

	public BigInteger getIdForm() {
        return idForm;
    }

    public void setIdForm(BigInteger idForm) {
        this.idForm = idForm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

	public BigInteger getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(BigInteger idEvent) {
		this.idEvent = idEvent;
	}
}
