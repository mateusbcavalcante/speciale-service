package br.com.a2dm.spdm.omie.service;

import java.math.BigInteger;

import br.com.a2dm.spdm.entity.WebhookLog;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoItemEventPayload;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoItemWebhookPayload;
import br.com.a2dm.spdm.service.TabelaPrecoItemService;
import br.com.a2dm.spdm.service.WebhookLogService;
import br.com.a2dm.spdm.utils.JsonUtils;

public class OmieTabelaPrecoItemWebhookService {

	private static final BigInteger ID_USUARIO_INTEGRACAO = BigInteger.ONE;

	private static OmieTabelaPrecoItemWebhookService instance;

	private OmieTabelaPrecoItemWebhookService() {
	}

	public static OmieTabelaPrecoItemWebhookService getInstance() {
		if (instance == null) {
			instance = new OmieTabelaPrecoItemWebhookService();
		}
		return instance;
	}

	public void processar(TabelaPrecoItemWebhookPayload payload) throws OmieServiceException {
		TabelaPrecoItemEventPayload event = payload.getEvent();
		if (event == null || event.getnCodTabPreco() == null || event.getnCodProd() == null) {
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
			boolean inativar = isTopicExcluido(payload.getTopic());

			String acao = TabelaPrecoItemService.getInstancia().sincronizarDoWebhook(event.getnCodTabPreco(),
					event.getnCodProd(), event.getnValorTabela(), event.getnValorOriginal(),
					event.getnValorCalculado(), event.getnPercAcrescimo(), event.getnPercDesconto(), inativar,
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
		return topic != null && topic.toLowerCase().contains("excluido");
	}
}
