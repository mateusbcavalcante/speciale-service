package br.com.a2dm.spdm.omie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayload;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayloadObj;
import br.com.a2dm.spdm.omie.repository.OmieTabelasPrecosRepository;;

public class OmieTabelaPrecoService {

	private static OmieTabelaPrecoService instance;

	private OmieTabelaPrecoService() {
	}

	public static OmieTabelaPrecoService getInstance() {
		if (instance == null) {
			instance = new OmieTabelaPrecoService();
		}
		return instance;
	}

	public List<TabelaPrecoPayload> listarTabelasPrecos() throws OmieServiceException {
		try {
			List<TabelaPrecoPayload> listTabelaPrecoPayload = new ArrayList<>();
			
			for (int i = 1; i <= 2; i++) {
				TabelaPrecoPayloadObj tabelaPrecoPayloadObjIn = OmieTabelasPrecosRepository.getInstance().listarTabelasPrecos(i);
				
				if (tabelaPrecoPayloadObjIn.getListaTabelasPreco() != null && tabelaPrecoPayloadObjIn.getListaTabelasPreco().size() > 0) {
					listTabelaPrecoPayload.addAll(tabelaPrecoPayloadObjIn.getListaTabelasPreco());
				}
			}
			return listTabelaPrecoPayload;
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}
	
	public TabelaPrecoPayload obterTabelaPreco(String tabelaPreco) throws OmieServiceException {
		List<TabelaPrecoPayload> listTabelaPrecoFiltrada = new ArrayList<>();
		List<TabelaPrecoPayload> listTabelaPreco = listarTabelasPrecos();
		
		if (listTabelaPreco != null && listTabelaPreco.size() > 0) {
			List<TabelaPrecoPayload> sortedList = listTabelaPreco.stream()
					.sorted((o1, o2)-> o1.getcNome().compareTo(o2.getcNome()))
					.collect(Collectors.toList());
			sortedList.forEach(System.out::println);
			
			listTabelaPrecoFiltrada = listTabelaPreco.stream()
					.filter(x -> x.getcNome().equalsIgnoreCase(tabelaPreco))
					.collect(Collectors.toList());
		}
		
		return isResultTabelaPreco(listTabelaPrecoFiltrada) ? listTabelaPrecoFiltrada.get(0) : null;
	}

	private boolean isResultTabelaPreco(List<TabelaPrecoPayload> listTabelaPrecoFiltrada) {
		return listTabelaPrecoFiltrada != null && listTabelaPrecoFiltrada.size() > 0;
	}
}
