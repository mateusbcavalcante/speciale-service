package br.com.a2dm.spdm.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "tb_webhook_log", schema = "ped")
@SequenceGenerator(name = "SQ_WEBHOOK_LOG", sequenceName = "SQ_WEBHOOK_LOG", allocationSize = 1)
@Proxy(lazy = true)
public class WebhookLog implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String STATUS_RECEBIDO = "RECEBIDO";
	public static final String STATUS_PROCESSADO = "PROCESSADO";
	public static final String STATUS_ERRO = "ERRO";
	public static final String STATUS_IGNORADO = "IGNORADO";

	public static final String ACAO_INCLUSAO = "INCLUSAO";
	public static final String ACAO_ALTERACAO = "ALTERACAO";
	public static final String ACAO_INATIVACAO = "INATIVACAO";
	public static final String ACAO_ERRO = "ERRO";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_WEBHOOK_LOG")
	@Column(name = "id_webhook_log")
	private BigInteger idWebhookLog;

	@Column(name = "message_id")
	private String messageId;

	@Column(name = "topic")
	private String topic;

	@Column(name = "id_externo")
	private BigInteger idExterno;

	@Column(name = "payload")
	private String payload;

	@Column(name = "des_acao")
	private String desAcao;

	@Column(name = "des_status")
	private String desStatus;

	@Column(name = "des_erro")
	private String desErro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_recebimento")
	private Date datRecebimento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dat_processamento")
	private Date datProcessamento;

	public BigInteger getIdWebhookLog() {
		return idWebhookLog;
	}

	public void setIdWebhookLog(BigInteger idWebhookLog) {
		this.idWebhookLog = idWebhookLog;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public BigInteger getIdExterno() {
		return idExterno;
	}

	public void setIdExterno(BigInteger idExterno) {
		this.idExterno = idExterno;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getDesAcao() {
		return desAcao;
	}

	public void setDesAcao(String desAcao) {
		this.desAcao = desAcao;
	}

	public String getDesStatus() {
		return desStatus;
	}

	public void setDesStatus(String desStatus) {
		this.desStatus = desStatus;
	}

	public String getDesErro() {
		return desErro;
	}

	public void setDesErro(String desErro) {
		this.desErro = desErro;
	}

	public Date getDatRecebimento() {
		return datRecebimento;
	}

	public void setDatRecebimento(Date datRecebimento) {
		this.datRecebimento = datRecebimento;
	}

	public Date getDatProcessamento() {
		return datProcessamento;
	}

	public void setDatProcessamento(Date datProcessamento) {
		this.datProcessamento = datProcessamento;
	}
}
