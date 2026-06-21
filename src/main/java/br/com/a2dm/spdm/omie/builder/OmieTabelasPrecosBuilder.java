package br.com.a2dm.spdm.omie.builder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
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

			if (jsonObject.has("nTotRegistros") && !jsonObject.isNull("nTotRegistros")) {
				tabelaPrecoPayloadObj.setnTotRegistros(new BigInteger(jsonObject.getString("nTotRegistros")));
			}

			if (!jsonObject.has("listaTabelasPreco") || jsonObject.isNull("listaTabelasPreco")) {
				tabelaPrecoPayloadObj.setListaTabelasPreco(tabelasPrecos);
				return tabelaPrecoPayloadObj;
			}

			JSONArray itensTabela = jsonObject.getJSONArray("listaTabelasPreco");
			for (int i = 0; i < itensTabela.length(); i++) {
				JSONObject tabelaPrecoObj = itensTabela.getJSONObject(i);
				tabelasPrecos.add(buildTabelaPreco(tabelaPrecoObj));
			}

			tabelaPrecoPayloadObj.setListaTabelasPreco(tabelasPrecos);
			return tabelaPrecoPayloadObj;
		} catch (Exception e) {
			throw new OmieBuilderException(e);
		}
	}

	private TabelaPrecoPayload buildTabelaPreco(JSONObject tabelaPrecoObj) throws JSONException {
		TabelaPrecoPayload tabelaPreco = new TabelaPrecoPayload();

		if (tabelaPrecoObj.has("nCodTabPreco") && !tabelaPrecoObj.isNull("nCodTabPreco")) {
			tabelaPreco.setnCodTabPreco(new BigInteger(String.valueOf(tabelaPrecoObj.getLong("nCodTabPreco"))));
		}
		if (tabelaPrecoObj.has("cCodigo") && !tabelaPrecoObj.isNull("cCodigo")) {
			tabelaPreco.setcCodigo(tabelaPrecoObj.getString("cCodigo"));
		}
		if (tabelaPrecoObj.has("cNome") && !tabelaPrecoObj.isNull("cNome")) {
			tabelaPreco.setcNome(tabelaPrecoObj.getString("cNome"));
		}
		if (tabelaPrecoObj.has("cOrigem") && !tabelaPrecoObj.isNull("cOrigem")) {
			tabelaPreco.setcOrigem(tabelaPrecoObj.getString("cOrigem"));
		}
		if (tabelaPrecoObj.has("cAtiva") && !tabelaPrecoObj.isNull("cAtiva")) {
			tabelaPreco.setcAtiva(tabelaPrecoObj.getString("cAtiva"));
		}

		if (tabelaPrecoObj.has("outrasInfo") && !tabelaPrecoObj.isNull("outrasInfo")) {
			JSONObject outrasInfo = tabelaPrecoObj.getJSONObject("outrasInfo");
			if (outrasInfo.has("nPercAcrescimo") && !outrasInfo.isNull("nPercAcrescimo")) {
				tabelaPreco.setnPercAcrescimo(outrasInfo.getDouble("nPercAcrescimo"));
			}
			if (outrasInfo.has("nPercDesconto") && !outrasInfo.isNull("nPercDesconto")) {
				tabelaPreco.setnPercDesconto(outrasInfo.getDouble("nPercDesconto"));
			}
		}

		return tabelaPreco;
	}
}
