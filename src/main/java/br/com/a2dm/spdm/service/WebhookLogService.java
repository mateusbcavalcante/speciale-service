package br.com.a2dm.spdm.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.WebhookLog;

public class WebhookLogService extends A2DMHbNgc<WebhookLog> {

	private static WebhookLogService instancia = null;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();

	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();

	public static WebhookLogService getInstancia() {
		if (instancia == null) {
			instancia = new WebhookLogService();
		}
		return instancia;
	}

	public WebhookLogService() {
		adicionarFiltro("idWebhookLog", RestritorHb.RESTRITOR_EQ, "idWebhookLog");
		adicionarFiltro("messageId", RestritorHb.RESTRITOR_EQ, "messageId");
		adicionarFiltro("topic", RestritorHb.RESTRITOR_EQ, "topic");
		adicionarFiltro("desStatus", RestritorHb.RESTRITOR_EQ, "desStatus");
	}

	/**
	 * Indica se a mensagem já foi processada com sucesso (controle de idempotência).
	 */
	public boolean isMensagemProcessada(String messageId) throws Exception {
		if (messageId == null || messageId.trim().isEmpty()) {
			return false;
		}

		WebhookLog filtro = new WebhookLog();
		filtro.setMessageId(messageId);
		filtro.setDesStatus(WebhookLog.STATUS_PROCESSADO);

		return this.get(filtro, 0) != null;
	}

	/**
	 * Registra o recebimento de um webhook (status RECEBIDO) para auditoria.
	 */
	public WebhookLog registrarRecebimento(String messageId, String topic, BigInteger idExterno, String payload)
			throws Exception {
		WebhookLog log = new WebhookLog();
		log.setMessageId(messageId);
		log.setTopic(topic);
		log.setIdExterno(idExterno);
		log.setPayload(payload);
		log.setDesStatus(WebhookLog.STATUS_RECEBIDO);
		log.setDatRecebimento(new Date());
		return this.inserir(log);
	}

	/**
	 * Marca o log como processado com sucesso, registrando a ação local executada.
	 */
	public void registrarProcessado(WebhookLog log, String acao) throws Exception {
		if (log == null) {
			return;
		}
		log.setDesStatus(WebhookLog.STATUS_PROCESSADO);
		log.setDesAcao(acao);
		log.setDatProcessamento(new Date());
		this.alterar(log);
	}

	/**
	 * Marca o log como erro, registrando a mensagem para posterior reprocessamento.
	 */
	public void registrarErro(WebhookLog log, String mensagemErro) throws Exception {
		if (log == null) {
			return;
		}
		log.setDesStatus(WebhookLog.STATUS_ERRO);
		log.setDesAcao(WebhookLog.ACAO_ERRO);
		log.setDesErro(mensagemErro);
		log.setDatProcessamento(new Date());
		this.alterar(log);
	}

	/**
	 * Marca o log como ignorado (evento sem ação local necessária).
	 */
	public void registrarIgnorado(WebhookLog log) throws Exception {
		if (log == null) {
			return;
		}
		log.setDesStatus(WebhookLog.STATUS_IGNORADO);
		log.setDatProcessamento(new Date());
		this.alterar(log);
	}

	@Override
	protected Criteria montaCriteria(Session sessao, int join) {
		return sessao.createCriteria(WebhookLog.class);
	}

	@Override
	protected void setarOrdenacao(Criteria criteria, WebhookLog vo, int join) {
		criteria.addOrder(Order.desc("datRecebimento"));
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map restritores() {
		return restritores;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map filtroPropriedade() {
		return filtroPropriedade;
	}
}
