package br.com.a2dm.spdm.ativmob.api;

import br.com.a2dm.spdm.api.ApiClient;
import br.com.a2dm.spdm.api.ApiClientException;
import br.com.a2dm.spdm.api.ApiClientResponse;
import br.com.a2dm.spdm.omie.api.OmieApiError;
import br.com.a2dm.spdm.omie.api.OmieApiPayload;
import br.com.a2dm.spdm.utils.AuthUtils;
import br.com.a2dm.spdm.utils.JsonUtils;
import org.codehaus.jettison.json.JSONObject;

public class AtivMobApiClient {

    private static final String ATIV_MOB_API_PATH = "https://api4.ativmob.com.br/v2";
    private static final String ATIV_MOB_X_API_KEY = "X-API-Key";
    private static final String ATIV_MOB_X_API_VALUE = "20ea60bd-42c9-40b0-a7df-02107c48d247";
    private ApiClient apiClient;

    public AtivMobApiClient() {
        this.apiClient = new ApiClient(ATIV_MOB_API_PATH);
    }

    public ApiClientResponse get(String endpoint) throws ApiClientException {
        try {
            AuthUtils<String, String> auth = new AuthUtils<>(ATIV_MOB_X_API_KEY, ATIV_MOB_X_API_VALUE);
            ApiClientResponse response = this.apiClient.get(endpoint, auth);
            return response;
        } catch (Exception e) {
            throw new ApiClientException(e);
        }
    }

}
