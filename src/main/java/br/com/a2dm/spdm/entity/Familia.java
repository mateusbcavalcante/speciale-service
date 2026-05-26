package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "tb_familia", schema = "ped")
@SequenceGenerator(name = "SQ_FAMILIA", sequenceName = "SQ_FAMILIA", allocationSize = 1)
@Proxy(lazy = true)
public class Familia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FAMILIA")
	@Column(name = "id_familia")
	private BigInteger idFamilia;

	@Column(name = "des_familia")
	private String desFamilia;

	@Column(name = "flg_integral")
	private String flgIntegral;

	@Column(name = "id_externo")
	private BigInteger idExterno;

	public BigInteger getIdFamilia() {
		return idFamilia;
	}

	public void setIdFamilia(BigInteger idFamilia) {
		this.idFamilia = idFamilia;
	}

	public String getDesFamilia() {
		return desFamilia;
	}

	public void setDesFamilia(String desFamilia) {
		this.desFamilia = desFamilia;
	}

	public String getFlgIntegral() {
		return flgIntegral;
	}

	public void setFlgIntegral(String flgIntegral) {
		this.flgIntegral = flgIntegral;
	}

	public boolean isIntegral() {
		return flgIntegral != null && flgIntegral.equalsIgnoreCase("S");
	}

	public BigInteger getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(BigInteger idExterno) {
		this.idExterno = idExterno;
	}
}
