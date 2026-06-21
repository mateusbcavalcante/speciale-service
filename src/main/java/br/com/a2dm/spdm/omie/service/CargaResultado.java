package br.com.a2dm.spdm.omie.service;

import java.util.ArrayList;
import java.util.List;

public class CargaResultado {

	private int processados;
	private int incluidos;
	private int atualizados;
	private int inativados;
	private int ignorados;
	private int erros;
	private List<String> mensagensErro;

	public CargaResultado() {
		this.mensagensErro = new ArrayList<>();
	}

	public void contabilizar(String acao) {
		this.processados++;
		if ("INCLUSAO".equals(acao)) {
			this.incluidos++;
		} else if ("ALTERACAO".equals(acao)) {
			this.atualizados++;
		} else if ("INATIVACAO".equals(acao)) {
			this.inativados++;
		} else {
			this.ignorados++;
		}
	}

	public void contabilizarErro(String mensagem) {
		this.processados++;
		this.erros++;
		if (mensagem != null) {
			this.mensagensErro.add(mensagem);
		}
	}

	public int getProcessados() {
		return processados;
	}

	public void setProcessados(int processados) {
		this.processados = processados;
	}

	public int getIncluidos() {
		return incluidos;
	}

	public void setIncluidos(int incluidos) {
		this.incluidos = incluidos;
	}

	public int getAtualizados() {
		return atualizados;
	}

	public void setAtualizados(int atualizados) {
		this.atualizados = atualizados;
	}

	public int getInativados() {
		return inativados;
	}

	public void setInativados(int inativados) {
		this.inativados = inativados;
	}

	public int getIgnorados() {
		return ignorados;
	}

	public void setIgnorados(int ignorados) {
		this.ignorados = ignorados;
	}

	public int getErros() {
		return erros;
	}

	public void setErros(int erros) {
		this.erros = erros;
	}

	public List<String> getMensagensErro() {
		return mensagensErro;
	}

	public void setMensagensErro(List<String> mensagensErro) {
		this.mensagensErro = mensagensErro;
	}
}
