package br.com.a2dm.spdm.omie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import br.com.a2dm.spdm.api.ApiClientException;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayload;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayloadObj;
import br.com.a2dm.spdm.omie.repository.OmieRepositoryException;
import br.com.a2dm.spdm.omie.repository.OmieTabelasPrecosRepository;

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
			
			for (int i = 1; i <= 14; i++) {
				TabelaPrecoPayloadObj tabelaPrecoPayloadObjIn = null;
				int maxRetries = 3;
				int retryCount = 0;
				boolean success = false;
				
				while (!success && retryCount < maxRetries) {
					try {
						tabelaPrecoPayloadObjIn = OmieTabelasPrecosRepository.getInstance().listarTabelasPrecos(i);
						success = true;
					} catch (OmieRepositoryException e) {
						if (isConsumoRedundanteError(e) && retryCount < maxRetries - 1) {
							int waitSeconds = extractWaitTime(e);
							System.out.println(String.format("Erro de consumo redundante detectado na página %d. Aguardando %d segundos antes de tentar novamente...", i, waitSeconds));
							try {
								Thread.sleep(waitSeconds * 1000L);
							} catch (InterruptedException ie) {
								Thread.currentThread().interrupt();
								throw new OmieServiceException("Thread interrompida durante espera", ie);
							}
							retryCount++;
						} else {
							throw new OmieServiceException(e);
						}
					}
				}
				
				if (tabelaPrecoPayloadObjIn != null && tabelaPrecoPayloadObjIn.getListaTabelasPreco() != null && tabelaPrecoPayloadObjIn.getListaTabelasPreco().size() > 0) {
					listTabelaPrecoPayload.addAll(tabelaPrecoPayloadObjIn.getListaTabelasPreco());
				}
				
				// Pequeno delay entre requisições para evitar rate limiting
				if (i < 14) {
					try {
						Thread.sleep(500); // 500ms entre requisições
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
						throw new OmieServiceException("Thread interrompida durante delay entre requisições", ie);
					}
				}
			}
			return listTabelaPrecoPayload;
		} catch (OmieServiceException e) {
			throw e;
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
	
	private boolean isConsumoRedundanteError(OmieRepositoryException e) {
		if (e.getCause() != null) {
			Throwable cause = e.getCause();
			if (cause instanceof ApiClientException) {
				String message = cause.getMessage();
				return message != null && message.contains("Consumo redundante detectado");
			}
		}
		// Também verifica a mensagem da própria exceção
		String message = e.getMessage();
		return message != null && message.contains("Consumo redundante detectado");
	}
	
	private int extractWaitTime(OmieRepositoryException e) {
		try {
			String message = null;
			
			// Tenta obter a mensagem da causa (ApiClientException)
			if (e.getCause() != null && e.getCause() instanceof ApiClientException) {
				message = e.getCause().getMessage();
			}
			
			// Se não encontrou, tenta a mensagem da própria exceção
			if (message == null) {
				message = e.getMessage();
			}
			
			if (message != null && message.contains("Aguarde")) {
				// Extrai o número de segundos da mensagem: "Aguarde 35 segundos"
				Pattern pattern = Pattern.compile("Aguarde\\s+(\\d+)\\s+segundos");
				Matcher matcher = pattern.matcher(message);
				if (matcher.find()) {
					return Integer.parseInt(matcher.group(1));
				}
			}
		} catch (Exception ex) {
			// Se não conseguir extrair, retorna um valor padrão
		}
		return 35; // Valor padrão de 35 segundos
	}
}
