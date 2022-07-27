package br.com.a2dm.spdm.omie.builder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayload;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayloadObj;
import br.com.a2dm.spdm.utils.JsonUtils;

public class OmieTabelasPrecosBuilder {

	public OmieTabelasPrecosBuilder() {
	}

	public TabelaPrecoPayloadObj buildTabelasPrecos(String json) {
		try {
			TabelaPrecoPayloadObj tabelaPrecoPayloadObj = new TabelaPrecoPayloadObj();
			List<TabelaPrecoPayload> tabelasPrecos = new ArrayList<>();
			
			JSONObject jsonObject = JsonUtils.parse(json);
			tabelaPrecoPayloadObj.setnTotRegistros(new BigInteger(jsonObject.getString("nTotRegistros")));
			JSONArray itensTabela = (JSONArray) jsonObject.getJSONArray("listaTabelasPreco");
			
			for (int i = 0; i < itensTabela.length(); i++) {
				JSONObject tabelaPrecoObj = (JSONObject) itensTabela.get(i);
				
				TabelaPrecoPayload tabelaPreco = new TabelaPrecoPayload();
				tabelaPreco.setnCodTabPreco(new BigInteger(tabelaPrecoObj.getString("nCodTabPreco")));
				tabelaPreco.setcNome(tabelaPrecoObj.getString("cNome"));
				
				tabelasPrecos.add(tabelaPreco);
			}
			tabelaPrecoPayloadObj.setListaTabelasPreco(tabelasPrecos);
			return tabelaPrecoPayloadObj;
		} catch (Exception e) {
			throw new OmieBuilderException(e);
		}
	}
}
