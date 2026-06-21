package br.com.a2dm.spdm.omie.payload;

import java.util.ArrayList;
import java.util.List;

import br.com.a2dm.brcmn.dto.ProdutoDTO;

public class ListaProdutosOmiePayload {

	private int totalDePaginas;
	private List<ProdutoDTO> produtos;

	public ListaProdutosOmiePayload() {
		this.produtos = new ArrayList<>();
	}

	public int getTotalDePaginas() {
		return totalDePaginas;
	}

	public void setTotalDePaginas(int totalDePaginas) {
		this.totalDePaginas = totalDePaginas;
	}

	public List<ProdutoDTO> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoDTO> produtos) {
		this.produtos = produtos;
	}
}
