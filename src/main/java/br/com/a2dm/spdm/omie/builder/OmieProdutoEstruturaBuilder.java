package br.com.a2dm.spdm.omie.builder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.com.a2dm.brcmn.dto.IdentDTO;
import br.com.a2dm.brcmn.dto.ProdutoEstruturaDTO;
import br.com.a2dm.spdm.utils.JsonUtils;

public class OmieProdutoEstruturaBuilder {
	
	public OmieProdutoEstruturaBuilder() {
		
	}
	
	public ProdutoEstruturaDTO buildProdutoEstrutura(String json) {
		try {
			JSONObject jsonObject = JsonUtils.parse(json);
			String unidProduto = jsonObject.getJSONObject("ident").getString("unidProduto");
			return new ProdutoEstruturaDTO(new IdentDTO(unidProduto));
		} catch (JSONException e) {
			throw new OmieBuilderException(e);
		}
	}

}
