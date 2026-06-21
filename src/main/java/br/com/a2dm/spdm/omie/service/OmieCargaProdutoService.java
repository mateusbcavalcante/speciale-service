package br.com.a2dm.spdm.omie.service;

import java.math.BigInteger;

import br.com.a2dm.brcmn.dto.ProdutoDTO;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.omie.api.OmieApiClient;
import br.com.a2dm.spdm.omie.builder.OmieProdutosBuilder;
import br.com.a2dm.spdm.omie.payload.ListaProdutosOmiePayload;
import br.com.a2dm.spdm.omie.payload.ListarProdutosPayload;
import br.com.a2dm.spdm.service.ProdutoService;

public class OmieCargaProdutoService {

	private static final BigInteger ID_USUARIO_INTEGRACAO = BigInteger.ONE;
	private static final int REGISTROS_POR_PAGINA = 50;

	private static OmieCargaProdutoService instance;

	private OmieCargaProdutoService() {
	}

	public static OmieCargaProdutoService getInstance() {
		if (instance == null) {
			instance = new OmieCargaProdutoService();
		}
		return instance;
	}

	public CargaResultado executar() throws OmieServiceException {
		try {
			CargaResultado resultado = new CargaResultado();

			int pagina = 1;
			int totalPaginas = 1;

			do {
				ListaProdutosOmiePayload paginaAtual = listarPagina(pagina);
				totalPaginas = paginaAtual.getTotalDePaginas() > 0 ? paginaAtual.getTotalDePaginas() : 1;

				for (ProdutoDTO produto : paginaAtual.getProdutos()) {
					sincronizarProduto(produto, resultado);
				}

				pagina++;
				if (pagina <= totalPaginas) {
					aguardarEntreRequisicoes();
				}
			} while (pagina <= totalPaginas);

			return resultado;
		} catch (OmieServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}

	private void sincronizarProduto(ProdutoDTO produto, CargaResultado resultado) {
		try {
			boolean inativar = "N".equalsIgnoreCase(produto.getFlgAtivo());
			String acao = ProdutoService.getInstancia().sincronizarDoWebhook(produto.getIdProduto(),
					produto.getDesProduto(), produto.getUnidade(), produto.getValorUnitario(),
					produto.getCodigoFamiliaOmie(), inativar, ID_USUARIO_INTEGRACAO);
			resultado.contabilizar(acao);
		} catch (Exception e) {
			resultado.contabilizarErro("Produto " + produto.getIdProduto() + ": " + e.getMessage());
		}
	}

	private ListaProdutosOmiePayload listarPagina(int pagina) throws OmieServiceException {
		try {
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/geral/produtos/", "ListarProdutos",
					new ListarProdutosPayload(pagina, REGISTROS_POR_PAGINA));
			return new OmieProdutosBuilder().buildListaProdutos(response.getBody());
		} catch (Exception e) {
			throw new OmieServiceException("Erro ao listar produtos na Omie (página " + pagina + ")", e);
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
