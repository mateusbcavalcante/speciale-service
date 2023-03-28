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
@Table(name = "tb_item", schema="ped")
@SequenceGenerator(name = "SQ_ITEM", sequenceName = "SQ_ITEM", allocationSize = 1)
@Proxy(lazy = true)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ITEM")
    @Column(name = "id_item")
    private BigInteger idItem;

    @Column(name = "type")
    private String type;

    @Column(name = "label")
    private String label;

    @Column(name = "url")
    private String url;

    @Column(name = "value")
    private Integer value;
    
    @Column(name = "codigo")
    private Integer codigo;
    
    @Column(name = "integ_id")
    private Integer integId;
    
    @Column(name = "id_sugestao_pedido")
	private BigInteger idSugestaoPedido;

    @JsonIgnore(value = true)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sugestao_pedido", insertable = false, updatable = false)
	private SugestaoPedido sugestaoPedido;
    
    public Item() {
		super();
	}

    public Item(String type, String label, String url, Integer value, Integer codigo, Integer integId,
			BigInteger idSugestaoPedido, SugestaoPedido sugestaoPedido) {
		super();
		this.type = type;
		this.label = label;
		this.url = url;
		this.value = value;
		this.codigo = codigo;
		this.integId = integId;
		this.idSugestaoPedido = idSugestaoPedido;
		this.sugestaoPedido = sugestaoPedido;
	}

	public BigInteger getIdItem() {
		return idItem;
	}

	public void setIdItem(BigInteger idItem) {
		this.idItem = idItem;
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

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getIntegId() {
		return integId;
	}

	public void setIntegId(Integer integId) {
		this.integId = integId;
	}

	public BigInteger getIdSugestaoPedido() {
		return idSugestaoPedido;
	}

	public void setIdSugestaoPedido(BigInteger idSugestaoPedido) {
		this.idSugestaoPedido = idSugestaoPedido;
	}

	public SugestaoPedido getSugestaoPedido() {
		return sugestaoPedido;
	}

	public void setSugestaoPedido(SugestaoPedido sugestaoPedido) {
		this.sugestaoPedido = sugestaoPedido;
	}

}
