package br.com.a2dm.spdm.omie.service;

import java.math.BigInteger;

import br.com.a2dm.spdm.entity.WebhookLog;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoEventPayload;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoWebhookPayload;
import br.com.a2dm.spdm.service.TabelaPrecoService;
import br.com.a2dm.spdm.service.WebhookLogService;
import br.com.a2dm.spdm.utils.JsonUtils;

public class OmieTabelaPrecoWebhookService {

	private static final BigInteger ID_USUARIO_INTEGRACAO = BigInteger.ONE;

	private static OmieTabelaPrecoWebhookService instance;

	private OmieTabelaPrecoWebhookService() {
	}

	public static OmieTabelaPrecoWebhookService getInstance() {
		if (instance == null) {
			instance = new OmieTabelaPrecoWebhookService();
		}
		return instance;
	}

	public void processar(TabelaPrecoWebhookPayload payload) throws OmieServiceException {
		TabelaPrecoEventPayload event = payload.getEvent();
		if (event == null || event.getnCodTabPreco() == null) {
			return;
		}

		try {
			if (WebhookLogService.getInstancia().isMensagemProcessada(payload.getMessageId())) {
				return;
			}
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}

		WebhookLog log;
		try {
			log = WebhookLogService.getInstancia().registrarRecebimento(payload.getMessageId(), payload.getTopic(),
					event.getnCodTabPreco(), JsonUtils.toJson(payload));
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}

		try {
			boolean inativar = isTopicExcluido(payload.getTopic()) || "N".equalsIgnoreCase(event.getcAtiva());

			Double perAcrescimo = event.getOutrasInfo() != null ? event.getOutrasInfo().getnPercAcrescimo() : null;
			Double perDesconto = event.getOutrasInfo() != null ? event.getOutrasInfo().getnPercDesconto() : null;

			String acao = TabelaPrecoService.getInstancia().sincronizarDoWebhook(event.getnCodTabPreco(),
					event.getcCodigo(), event.getcNome(), event.getcOrigem(), perAcrescimo, perDesconto, inativar,
					ID_USUARIO_INTEGRACAO);

			WebhookLogService.getInstancia().registrarProcessado(log, acao);
		} catch (Exception e) {
			registrarErro(log, e);
		}
	}

	private void registrarErro(WebhookLog log, Exception e) throws OmieServiceException {
		try {
			WebhookLogService.getInstancia().registrarErro(log, e.getMessage());
		} catch (Exception ex) {
			throw new OmieServiceException(ex);
		}
	}

	private boolean isTopicExcluido(String topic) {
		return topic != null && topic.toLowerCase().contains("excluida");
	}
}
