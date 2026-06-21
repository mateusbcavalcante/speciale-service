package br.com.a2dm.spdm.omie.builder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import br.com.a2dm.brcmn.domain.OmieCaracteristicaProduto;
import br.com.a2dm.brcmn.dto.ProdutoDTO;
import br.com.a2dm.spdm.omie.payload.ItemTabelaOmiePayload;
import br.com.a2dm.spdm.omie.payload.ListaProdutosOmiePayload;
import br.com.a2dm.spdm.utils.JsonUtils;

public class OmieProdutosBuilder {

	public OmieProdutosBuilder() {
	}

	public ListaProdutosOmiePayload buildListaProdutos(String json) {
		try {
			ListaProdutosOmiePayload resultado = new ListaProdutosOmiePayload();

			JSONObject jsonObject = JsonUtils.parse(json);

			if (jsonObject.has("total_de_paginas")) {
				resultado.setTotalDePaginas(jsonObject.getInt("total_de_paginas"));
			}

			if (!jsonObject.has("produto_servico_cadastro")) {
				return resultado;
			}

			JSONArray cadastros = jsonObject.getJSONArray("produto_servico_cadastro");
			for (int i = 0; i < cadastros.length(); i++) {
				JSONObject produto = (JSONObject) cadastros.get(i);

				ProdutoDTO produtoDTO = new ProdutoDTO();

				if (produto.has("codigo_produto")) {
					produtoDTO.setIdProduto(new BigInteger(produto.getString("codigo_produto")));
				}
				if (produto.has("descricao")) {
					produtoDTO.setDesProduto(produto.getString("descricao"));
				}
				if (produto.has("unidade")) {
					produtoDTO.setUnidade(produto.getString("unidade"));
				}
				if (produto.has("valor_unitario")) {
					produtoDTO.setValorUnitario(produto.getDouble("valor_unitario"));
				}
				if (produto.has("codigo_familia") && !produto.getString("codigo_familia").isEmpty()
						&& !"0".equals(produto.getString("codigo_familia"))) {
					produtoDTO.setCodigoFamiliaOmie(new BigInteger(produto.getString("codigo_familia")));
				}
				if (produto.has("descricao_familia")) {
					produtoDTO.setDescricaoFamilia(produto.getString("descricao_familia"));
				}

				boolean inativo = produto.has("inativo") && "S".equalsIgnoreCase(produto.getString("inativo"));
				produtoDTO.setFlgAtivo(inativo ? "N" : "S");

				resultado.getProdutos().add(produtoDTO);
			}

			return resultado;
		} catch (Exception e) {
			throw new OmieBuilderException(e);
		}
	}

	public List<ProdutoDTO> buildProdutos(String json) {
		try {
			
			List<ProdutoDTO> produtos = new ArrayList<>();
			
			JSONObject jsonObject = JsonUtils.parse(json);
			JSONObject listaTabelaPreco = (JSONObject)jsonObject.get("listaTabelaPreco");
			JSONArray itensTabela = (JSONArray) listaTabelaPreco.getJSONArray("itensTabela");
			
			for(int i = 0; i < itensTabela.length(); i++) {
				JSONObject produto = (JSONObject) itensTabela.get(i);
				
				ProdutoDTO produtoDTO = new ProdutoDTO();
				produtoDTO.setIdProduto(new BigInteger(produto.getString("nCodProd")));
				produtoDTO.setDesProduto(produto.getString("cDescricaoProduto"));
				produtoDTO.setValorUnitario(produto.getDouble("nValorTabela"));
				produtoDTO.setFlgAtivo("S");
				
				produtos.add(produtoDTO);
			}
			return produtos;
		} catch (Exception e) {
			throw new OmieBuilderException(e);
		}
	}
	
	public List<ItemTabelaOmiePayload> buildItensTabela(String json) {
		try {
			List<ItemTabelaOmiePayload> itens = new ArrayList<>();

			JSONObject jsonObject = JsonUtils.parse(json);
			if (!jsonObject.has("listaTabelaPreco")) {
				return itens;
			}

			JSONObject listaTabelaPreco = (JSONObject) jsonObject.get("listaTabelaPreco");
			if (!listaTabelaPreco.has("itensTabela")) {
				return itens;
			}

			JSONArray itensTabela = listaTabelaPreco.getJSONArray("itensTabela");
			for (int i = 0; i < itensTabela.length(); i++) {
				JSONObject itemJson = (JSONObject) itensTabela.get(i);

				ItemTabelaOmiePayload item = new ItemTabelaOmiePayload();
				item.setnCodProd(new BigInteger(String.valueOf(itemJson.getLong("nCodProd"))));

				if (itemJson.has("nValorTabela")) {
					item.setnValorTabela(itemJson.getDouble("nValorTabela"));
				}
				if (itemJson.has("nValorOriginal")) {
					item.setnValorOriginal(itemJson.getDouble("nValorOriginal"));
				}
				if (itemJson.has("nValorCalculado")) {
					item.setnValorCalculado(itemJson.getDouble("nValorCalculado"));
				}
				if (itemJson.has("nPercAcrescimo")) {
					item.setnPercAcrescimo(itemJson.getDouble("nPercAcrescimo"));
				}
				if (itemJson.has("nPercDesconto")) {
					item.setnPercDesconto(itemJson.getDouble("nPercDesconto"));
				}

				itens.add(item);
			}

			return itens;
		} catch (Exception e) {
			throw new OmieBuilderException(e);
		}
	}

	public ProdutoDTO buildConsultarProduto(String json) {
		try {
			JSONObject jsonObject = JsonUtils.parse(json);
			ProdutoDTO produtoDTO = new ProdutoDTO();

			if (jsonObject.has("codigo_produto") && !jsonObject.getString("codigo_produto").isEmpty()) {
				produtoDTO.setIdProduto(new BigInteger(jsonObject.getString("codigo_produto")));
			}

			if (jsonObject.has("descricao")) {
				produtoDTO.setDesProduto(jsonObject.getString("descricao"));
			} else if (jsonObject.has("cDescricao")) {
				produtoDTO.setDesProduto(jsonObject.getString("cDescricao"));
			}

			if (jsonObject.has("unidade")) {
				produtoDTO.setUnidade(jsonObject.getString("unidade"));
			}

			if (jsonObject.has("valor_unitario")) {
				produtoDTO.setValorUnitario(jsonObject.getDouble("valor_unitario"));
			}

			if (jsonObject.has("codigo_familia") && !jsonObject.getString("codigo_familia").isEmpty()) {
				produtoDTO.setCodigoFamiliaOmie(new BigInteger(jsonObject.getString("codigo_familia")));
			}

			if (jsonObject.has("descricao_familia")) {
				produtoDTO.setDescricaoFamilia(jsonObject.getString("descricao_familia"));
			}

			produtoDTO.setFlgAtivo("S");
			return produtoDTO;
		} catch (Exception e) {
			throw new OmieBuilderException(e);
		}
	}

	public Map<String,OmieCaracteristicaProduto> buildProdutoCaracteristicas(String json){
		try {
			
			Map<String, OmieCaracteristicaProduto> caracteristicas = new HashMap<>();
			
			JSONObject jsonObject = JsonUtils.parse(json);
			JSONArray listaCaracteristicas = (JSONArray) jsonObject.getJSONArray("listaCaracteristicas");
			
			for(int i = 0; i < listaCaracteristicas.length(); i++) {
				JSONObject caracteristicaJson = (JSONObject) listaCaracteristicas.get(i);
				
				OmieCaracteristicaProduto caracteristica = new OmieCaracteristicaProduto();
				caracteristica.setcNomeCaract(caracteristicaJson.getString("cNomeCaract"));
				caracteristica.setnCodCaract(caracteristicaJson.getLong("nCodProd"));
				caracteristica.setcConteudo(caracteristicaJson.getString("cConteudo"));
				
				caracteristicas.put(caracteristica.getcNomeCaract().toLowerCase(), caracteristica);
			}
			return caracteristicas;
		} catch (Exception e) {
			throw new OmieBuilderException(e);
		}
	}
	
	
	
}
