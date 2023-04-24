package br.com.a2dm.spdm.omie.service;

import java.math.BigInteger;

import br.com.a2dm.brcmn.dto.ProdutoEstruturaDTO;
import br.com.a2dm.spdm.omie.repository.OmieProdutoEstruturaRepository;

public class OmieProdutoEstruturaService {
	
	private static OmieProdutoEstruturaService instance;

	private OmieProdutoEstruturaService() {
	}

	public static OmieProdutoEstruturaService getInstance() {
		if (instance == null) {
			instance = new OmieProdutoEstruturaService();
		}
		return instance;
	}
	
	public ProdutoEstruturaDTO obterProdutoEstrutura(BigInteger idProduto) throws OmieServiceException {
		try {
			return OmieProdutoEstruturaRepository.getInstance().obterProdutoEstrutura(idProduto);
		} catch (Exception e) {
			throw new OmieServiceException(e);
		}
	}
	
}
