package br.com.a2dm.spdm.ativmob.repository;

import br.com.a2dm.brcmn.dto.ativmob.EventsDTO;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.ativmob.api.AtivMobApiClient;
import br.com.a2dm.spdm.ativmob.builder.AtivMobBuilder;
import br.com.a2dm.spdm.omie.repository.OmieRepositoryException;

import java.math.BigInteger;

public class AtivMobRepository {

    private static AtivMobRepository instance;
    private static String resource = "/orders/delivery/";
    private static String getEventsEndpoint = "get_events?storeCNPJ=";

    private AtivMobRepository() {
    }

    public static AtivMobRepository getInstance() {
        if(instance == null) {
            instance = new AtivMobRepository();
        }
        return instance;
    }

    public EventsDTO buscarEventos(BigInteger storeCNPJ) throws AtivMobRepositoryException {
        try {
            AtivMobApiClient apiClient = new AtivMobApiClient();
            ApiClientResponse response = apiClient.get(resource + getEventsEndpoint + storeCNPJ);
            return new AtivMobBuilder().buildBuscarEventosResponse(response.getBody());
        } catch (Exception e) {
            throw new AtivMobRepositoryException(e);
        }
    }
}
