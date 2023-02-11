package br.com.a2dm.spdm.ativmob.builder;

import br.com.a2dm.brcmn.dto.ClienteIntegracaoDTO;
import br.com.a2dm.brcmn.dto.ativmob.EventsDTO;
import br.com.a2dm.spdm.omie.builder.OmieBuilderException;
import br.com.a2dm.spdm.utils.JsonUtils;
import org.codehaus.jettison.json.JSONObject;

public class AtivMobBuilder {

    public EventsDTO buildBuscarEventosResponse(String json) throws AtivMobBuilderException{
        try {
            JSONObject jsonObject = JsonUtils.parse(json);
            return this.buildClienteIntegracaoDTO(jsonObject);
        } catch (Exception e) {
            throw new AtivMobBuilderException(e);
        }
    }

}
