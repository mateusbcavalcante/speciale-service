package br.com.a2dm.spdm.omie.repository;

import java.math.BigInteger;
import java.util.Map;

import br.com.a2dm.brcmn.domain.OmieCaracteristicaCliente;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.omie.api.OmieApiClient;
import br.com.a2dm.spdm.omie.builder.OmieClienteCaractBuilder;
import br.com.a2dm.spdm.omie.payload.ConsultarPayloadClienteCaract;

public class OmieClienteCaractRepository {

	private static OmieClienteCaractRepository instance;

	private OmieClienteCaractRepository() {
	}

	public static OmieClienteCaractRepository getInstance() {
		if (instance == null) {
			instance = new OmieClienteCaractRepository();
		}
		return instance;
	}

	public Map<String, OmieCaracteristicaCliente> obterCaracteristicasCliente(BigInteger codigoClienteOmie)
			throws OmieRepositoryException {
		try {
			ConsultarPayloadClienteCaract payload = new OmieClienteCaractBuilder().buildConsultar(codigoClienteOmie);
			OmieApiClient apiClient = new OmieApiClient();
			ApiClientResponse response = apiClient.post("/geral/clientescaract/", "ConsultarCaractCliente", payload);
			return new OmieClienteCaractBuilder().buildCaracteristicasResponse(response.getBody());
		} catch (Exception e) {
			throw new OmieRepositoryException(e);
		}
	}
}
