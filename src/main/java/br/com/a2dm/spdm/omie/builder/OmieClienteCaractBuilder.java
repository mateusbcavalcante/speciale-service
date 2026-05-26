package br.com.a2dm.spdm.omie.builder;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import br.com.a2dm.brcmn.domain.OmieCaracteristicaCliente;
import br.com.a2dm.spdm.omie.payload.ConsultarPayloadClienteCaract;
import br.com.a2dm.spdm.utils.JsonUtils;

public class OmieClienteCaractBuilder {

	public ConsultarPayloadClienteCaract buildConsultar(BigInteger codigoClienteOmie) throws OmieBuilderException {
		try {
			return new ConsultarPayloadClienteCaract(codigoClienteOmie);
		} catch (Exception e) {
			throw new OmieBuilderException("Erro ao montar json para ConsultarCaractCliente", e);
		}
	}

	public Map<String, OmieCaracteristicaCliente> buildCaracteristicasResponse(String json) {
		try {
			Map<String, OmieCaracteristicaCliente> caracteristicas = new HashMap<>();
			JSONObject jsonObject = JsonUtils.parse(json);

			if (!jsonObject.has("caracteristicas")) {
				return caracteristicas;
			}

			JSONArray lista = jsonObject.getJSONArray("caracteristicas");
			for (int i = 0; i < lista.length(); i++) {
				JSONObject item = lista.getJSONObject(i);
				OmieCaracteristicaCliente caracteristica = new OmieCaracteristicaCliente();
				caracteristica.setCampo(item.getString("campo"));
				caracteristica.setConteudo(item.getString("conteudo"));
				caracteristicas.put(caracteristica.getCampo().toLowerCase(), caracteristica);
			}
			return caracteristicas;
		} catch (Exception e) {
			throw new OmieBuilderException(e);
		}
	}
}
