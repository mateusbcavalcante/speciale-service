package br.com.a2dm.spdm.omie.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayload;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayloadObj;
import br.com.a2dm.spdm.omie.repository.OmieRepositoryException;
import br.com.a2dm.spdm.omie.repository.OmieTabelasPrecosRepository;

public class OmieTabelaPrecoService {

	private static final int REGISTROS_POR_PAGINA = 100;

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

			TabelaPrecoPayloadObj primeiraPagina = listarTabelasPrecosPagina(1);
			adicionarTabelasSePresentes(listTabelaPrecoPayload, primeiraPagina);

			int totalPaginas = calcularTotalPaginas(primeiraPagina.getnTotRegistros());

			for (int pagina = 2; pagina <= totalPaginas; pagina++) {
				TabelaPrecoPayloadObj paginaAtual = listarTabelasPrecosPagina(pagina);
				adicionarTabelasSePresentes(listTabelaPrecoPayload, paginaAtual);

				if (pagina < totalPaginas) {
					aguardarEntreRequisicoes();
				}
			}

			return listTabelaPrecoPayload;
		} catch (OmieServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}

	private int calcularTotalPaginas(BigInteger nTotRegistros) {
		if (nTotRegistros == null || nTotRegistros.compareTo(BigInteger.ZERO) <= 0) {
			return 0;
		}
		BigInteger registrosPorPagina = BigInteger.valueOf(REGISTROS_POR_PAGINA);
		return nTotRegistros.add(registrosPorPagina.subtract(BigInteger.ONE))
				.divide(registrosPorPagina)
				.intValue();
	}

	private TabelaPrecoPayloadObj listarTabelasPrecosPagina(int pagina) throws OmieServiceException {
		try {
			return OmieTabelasPrecosRepository.getInstance().listarTabelasPrecos(pagina);
		} catch (OmieRepositoryException e) {
			throw new OmieServiceException(e);
		}
	}

	private void adicionarTabelasSePresentes(List<TabelaPrecoPayload> destino, TabelaPrecoPayloadObj origem) {
		if (origem != null && origem.getListaTabelasPreco() != null && !origem.getListaTabelasPreco().isEmpty()) {
			destino.addAll(origem.getListaTabelasPreco());
		}
	}

	private void aguardarEntreRequisicoes() throws OmieServiceException {
		try {
			Thread.sleep(500);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			throw new OmieServiceException("Thread interrompida durante delay entre requisições", ie);
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
