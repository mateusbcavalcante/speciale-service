package br.com.a2dm.spdm.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "tb_aviso", schema="ped")
@SequenceGenerator(name = "SQ_AVISO", sequenceName = "SQ_AVISO", allocationSize = 1)
@Proxy(lazy = true)
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVISO")
    @Column(name = "id_aviso")
    private BigInteger id_aviso;
    
    @Column(name = "dat_aviso")
    private Date dat_aviso;

    @Column(name = "des_aviso")
    private String des_aviso;

    @Column(name = "ativo")
    private Boolean ativo;

	public BigInteger getId_aviso() {
		return id_aviso;
	}

	public void setId_aviso(BigInteger id_aviso) {
		this.id_aviso = id_aviso;
	}

	public Date getDat_aviso() {
		return dat_aviso;
	}

	public void setDat_aviso(Date dat_aviso) {
		this.dat_aviso = dat_aviso;
	}

	public String getDes_aviso() {
		return des_aviso;
	}

	public void setDes_aviso(String des_aviso) {
		this.des_aviso = des_aviso;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
    
    
}
