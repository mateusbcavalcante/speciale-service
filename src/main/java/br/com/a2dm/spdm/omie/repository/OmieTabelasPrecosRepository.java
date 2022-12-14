package br.com.a2dm.spdm.omie.repository;

import java.math.BigInteger;
import java.util.List;

import br.com.a2dm.brcmn.domain.OmieTabelaPrecoAll;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.omie.api.OmieApiClient;
import br.com.a2dm.spdm.omie.builder.OmieTabelasPrecosBuilder;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayload;

public class OmieTabelasPrecosRepository {

	private static OmieTabelasPrecosRepository instance;
	
	private OmieTabelasPrecosRepository() {
	}
	
	public static OmieTabelasPrecosRepository getInstance() {
		if(instance == null) {
			instance = new OmieTabelasPrecosRepository();
		}
		return instance;
	}
	
	public List<TabelaPrecoPayload> listarTabelasPrecos() throws OmieRepositoryException {
		try {
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/produtos/tabelaprecos/", "ListarTabelasPreco", new OmieTabelaPrecoAll(new BigInteger("1"), 
					                                                                                                            new BigInteger("500")));
			return new OmieTabelasPrecosBuilder().buildTabelasPrecos(response.getBody());
		} catch (Exception e) {
			throw new OmieRepositoryException(String.format("Erro ao listar tabelas de preços."), e);
		}
	}
}
