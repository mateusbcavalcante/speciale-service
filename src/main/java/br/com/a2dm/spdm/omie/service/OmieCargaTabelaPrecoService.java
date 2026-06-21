package br.com.a2dm.spdm.omie.service;

import java.math.BigInteger;
import java.util.List;

import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayload;
import br.com.a2dm.spdm.service.TabelaPrecoService;

public class OmieCargaTabelaPrecoService {

	private static final BigInteger ID_USUARIO_INTEGRACAO = BigInteger.ONE;

	private static OmieCargaTabelaPrecoService instance;

	private OmieCargaTabelaPrecoService() {
	}

	public static OmieCargaTabelaPrecoService getInstance() {
		if (instance == null) {
			instance = new OmieCargaTabelaPrecoService();
		}
		return instance;
	}

	public CargaResultado executar() throws OmieServiceException {
		try {
			CargaResultado resultado = new CargaResultado();

			List<TabelaPrecoPayload> tabelas = OmieTabelaPrecoService.getInstance().listarTabelasPrecos();
			if (tabelas != null) {
				for (TabelaPrecoPayload tabela : tabelas) {
					sincronizarTabela(tabela, resultado);
				}
			}

			return resultado;
		} catch (OmieServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}

	private void sincronizarTabela(TabelaPrecoPayload tabela, CargaResultado resultado) {
		try {
			String acao = TabelaPrecoService.getInstancia().sincronizarDoWebhook(tabela.getnCodTabPreco(),
					tabela.getcCodigo(), tabela.getcNome(), tabela.getcOrigem(), tabela.getnPercAcrescimo(),
					tabela.getnPercDesconto(), tabela.isInativa(), ID_USUARIO_INTEGRACAO);
			resultado.contabilizar(acao);
		} catch (Exception e) {
			resultado.contabilizarErro("Tabela de preço " + tabela.getnCodTabPreco() + ": " + e.getMessage());
		}
	}
}
