package br.com.a2dm.spdm.omie.service;

import java.math.BigInteger;
import java.util.List;

import br.com.a2dm.spdm.entity.TabelaPreco;
import br.com.a2dm.spdm.omie.payload.ItemTabelaOmiePayload;
import br.com.a2dm.spdm.omie.repository.OmieProdutosRepository;
import br.com.a2dm.spdm.service.TabelaPrecoItemService;
import br.com.a2dm.spdm.service.TabelaPrecoService;

public class OmieCargaTabelaPrecoItemService {

	private static final BigInteger ID_USUARIO_INTEGRACAO = BigInteger.ONE;

	private static OmieCargaTabelaPrecoItemService instance;

	private OmieCargaTabelaPrecoItemService() {
	}

	public static OmieCargaTabelaPrecoItemService getInstance() {
		if (instance == null) {
			instance = new OmieCargaTabelaPrecoItemService();
		}
		return instance;
	}

	public CargaResultado executar() throws OmieServiceException {
		try {
			CargaResultado resultado = new CargaResultado();

			List<TabelaPreco> tabelas = listarTabelasLocaisAtivas();
			if (tabelas != null) {
				for (TabelaPreco tabela : tabelas) {
					if (tabela.getIdExterno() == null) {
						continue;
					}
					sincronizarItensDaTabela(tabela.getIdExterno(), resultado);
					aguardarEntreRequisicoes();
				}
			}

			return resultado;
		} catch (OmieServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}

	private List<TabelaPreco> listarTabelasLocaisAtivas() throws Exception {
		TabelaPreco filtro = new TabelaPreco();
		filtro.setFlgAtivo("S");
		return TabelaPrecoService.getInstancia().pesquisar(filtro, 0);
	}

	private void sincronizarItensDaTabela(BigInteger nCodTabPreco, CargaResultado resultado) {
		List<ItemTabelaOmiePayload> itens;
		try {
			itens = OmieProdutosRepository.getInstance().listarItensTabelaPreco(nCodTabPreco);
		} catch (Exception e) {
			resultado.contabilizarErro("Tabela de preço " + nCodTabPreco + ": " + e.getMessage());
			return;
		}

		if (itens == null) {
			return;
		}

		for (ItemTabelaOmiePayload item : itens) {
			sincronizarItem(nCodTabPreco, item, resultado);
		}
	}

	private void sincronizarItem(BigInteger nCodTabPreco, ItemTabelaOmiePayload item, CargaResultado resultado) {
		try {
			String acao = TabelaPrecoItemService.getInstancia().sincronizarDoWebhook(nCodTabPreco, item.getnCodProd(),
					item.getnValorTabela(), item.getnValorOriginal(), item.getnValorCalculado(),
					item.getnPercAcrescimo(), item.getnPercDesconto(), false, ID_USUARIO_INTEGRACAO);
			resultado.contabilizar(acao);
		} catch (Exception e) {
			resultado.contabilizarErro(
					"Item tabela " + nCodTabPreco + " / produto " + item.getnCodProd() + ": " + e.getMessage());
		}
	}

	private void aguardarEntreRequisicoes() throws OmieServiceException {
		try {
			Thread.sleep(500);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			throw new OmieServiceException("Thread interrompida durante delay entre requisições", ie);
		}
	}
}
