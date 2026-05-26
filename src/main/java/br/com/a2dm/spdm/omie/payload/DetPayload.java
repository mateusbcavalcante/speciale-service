package br.com.a2dm.spdm.omie.payload;

public class DetPayload {

	private IdePayload ide;
	private ProdutoPayload produto;
	private InfAdicPayload inf_adic;

	public DetPayload(IdePayload ide, ProdutoPayload produto) {
		this(ide, produto, null);
	}

	public DetPayload(IdePayload ide, ProdutoPayload produto, InfAdicPayload inf_adic) {
		super();
		this.ide = ide;
		this.produto = produto;
		this.inf_adic = inf_adic;
	}

	public IdePayload getIde() {
		return ide;
	}

	public void setIde(IdePayload ide) {
		this.ide = ide;
	}

	public ProdutoPayload getProduto() {
		return produto;
	}

	public void setProduto(ProdutoPayload produto) {
		this.produto = produto;
	}

	public InfAdicPayload getInf_adic() {
		return inf_adic;
	}

	public void setInf_adic(InfAdicPayload inf_adic) {
		this.inf_adic = inf_adic;
	}
}
