package br.com.a2dm.spdm.omie.repository;

import java.math.BigInteger;

import br.com.a2dm.brcmn.dto.ProdutoEstruturaDTO;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.omie.api.OmieApiClient;
import br.com.a2dm.spdm.omie.builder.OmieProdutoEstruturaBuilder;
import br.com.a2dm.spdm.omie.payload.ProdutoEstruturaPayload;

public class OmieProdutoEstruturaRepository {
	
	private static OmieProdutoEstruturaRepository instance;
	
	private OmieProdutoEstruturaRepository() {
	}
	
	public static OmieProdutoEstruturaRepository getInstance() {
		if(instance == null) {
			instance = new OmieProdutoEstruturaRepository();
		}
		return instance;
	}

	public ProdutoEstruturaDTO obterProdutoEstrutura(BigInteger idProduto) throws OmieRepositoryException {
		try {
			ProdutoEstruturaPayload produtoEstrutura = new ProdutoEstruturaPayload(idProduto);
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/geral/malha/", "ConsultarEstrutura", produtoEstrutura);
			return new OmieProdutoEstruturaBuilder().buildProdutoEstrutura(response.getBody());
		} catch (Exception e) {
			throw new OmieRepositoryException(String.format("Erro ao obter produto estrutura por idProduto: %s", idProduto), e);
		}
	}
}
