package br.com.a2dm.spdm.omie.service;

import java.math.BigInteger;

import br.com.a2dm.spdm.entity.WebhookLog;
import br.com.a2dm.spdm.omie.payload.ProdutoEventPayload;
import br.com.a2dm.spdm.omie.payload.ProdutoWebhookPayload;
import br.com.a2dm.spdm.service.ProdutoService;
import br.com.a2dm.spdm.service.WebhookLogService;
import br.com.a2dm.spdm.utils.JsonUtils;

public class OmieProdutoWebhookService {

	private static final BigInteger ID_USUARIO_INTEGRACAO = BigInteger.ONE;

	private static OmieProdutoWebhookService instance;

	private OmieProdutoWebhookService() {
	}

	public static OmieProdutoWebhookService getInstance() {
		if (instance == null) {
			instance = new OmieProdutoWebhookService();
		}
		return instance;
	}

	public void processar(ProdutoWebhookPayload payload) throws OmieServiceException {
		ProdutoEventPayload event = payload.getEvent();
		if (event == null || event.getCodigo_produto() == null) {
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
					event.getCodigo_produto(), JsonUtils.toJson(payload));
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}

		try {
			boolean inativar = isTopicExcluido(payload.getTopic()) || "S".equalsIgnoreCase(event.getInativo());

			String acao = ProdutoService.getInstancia().sincronizarDoWebhook(event.getCodigo_produto(),
					event.getDescricao(), event.getUnidade(), event.getValor_unitario(), event.getCodigo_familia(),
					inativar, ID_USUARIO_INTEGRACAO);

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
