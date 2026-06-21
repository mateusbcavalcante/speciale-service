package br.com.a2dm.spdm.omie.payload;

public class ListarProdutosPayload {

	private int pagina;
	private int registros_por_pagina;
	private String apenas_importado_api;
	private String filtrar_apenas_omiepdv;

	public ListarProdutosPayload() {
		super();
	}

	public ListarProdutosPayload(int pagina, int registros_por_pagina) {
		super();
		this.pagina = pagina;
		this.registros_por_pagina = registros_por_pagina;
		this.apenas_importado_api = "N";
		this.filtrar_apenas_omiepdv = "N";
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public int getRegistros_por_pagina() {
		return registros_por_pagina;
	}

	public void setRegistros_por_pagina(int registros_por_pagina) {
		this.registros_por_pagina = registros_por_pagina;
	}

	public String getApenas_importado_api() {
		return apenas_importado_api;
	}

	public void setApenas_importado_api(String apenas_importado_api) {
		this.apenas_importado_api = apenas_importado_api;
	}

	public String getFiltrar_apenas_omiepdv() {
		return filtrar_apenas_omiepdv;
	}

	public void setFiltrar_apenas_omiepdv(String filtrar_apenas_omiepdv) {
		this.filtrar_apenas_omiepdv = filtrar_apenas_omiepdv;
	}
}
