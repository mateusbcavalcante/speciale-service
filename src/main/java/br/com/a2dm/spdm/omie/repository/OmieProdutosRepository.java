package br.com.a2dm.spdm.omie.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import br.com.a2dm.brcmn.domain.OmieCaracteristicaProduto;
import br.com.a2dm.brcmn.domain.OmieProduto;
import br.com.a2dm.brcmn.domain.OmieTabelaPreco;
import br.com.a2dm.brcmn.dto.ProdutoDTO;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.omie.api.OmieApiClient;
import br.com.a2dm.spdm.omie.builder.OmieProdutosBuilder;

public class OmieProdutosRepository {

	private static OmieProdutosRepository instance;
	
	private OmieProdutosRepository() {
	}
	
	public static OmieProdutosRepository getInstance() {
		if(instance == null) {
			instance = new OmieProdutosRepository();
		}
		return instance;
	}
	
	public List<ProdutoDTO> listarProdutosPorTabelaPreco(BigInteger idTabelaPreco) throws OmieRepositoryException {
		try {
			OmieTabelaPreco tabelaPreco = new OmieTabelaPreco();
			tabelaPreco.setnCodTabPreco(idTabelaPreco.toString());
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/produtos/tabelaprecos/", "ListarTabelaItens", tabelaPreco);
			return new OmieProdutosBuilder().buildProdutos(response.getBody());
		} catch (Exception e) {
			throw new OmieRepositoryException(String.format("Erro ao listar produtos por tabela de preço: %s", idTabelaPreco.toString()), e);
		}
	}
	
	public Map<String, OmieCaracteristicaProduto> obterCaracteristicasProduto(BigInteger codProduto) throws OmieRepositoryException {
		try {
			OmieProduto produto = new OmieProduto();
			produto.setnCodProd(codProduto.longValue());
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/geral/prodcaract/", "ListarCaractProduto", produto);
			return new OmieProdutosBuilder().buildProdutoCaracteristicas(response.getBody());
		} catch (Exception e) {
			throw new OmieRepositoryException(String.format("Erro ao obter características do produto: %d", codProduto), e);
		}
	}

}
