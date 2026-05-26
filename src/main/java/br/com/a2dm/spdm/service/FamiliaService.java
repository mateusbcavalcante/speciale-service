package br.com.a2dm.spdm.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.Familia;

public class FamiliaService extends A2DMHbNgc<Familia> {

	private static FamiliaService instancia;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();

	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();

	public static FamiliaService getInstancia() {
		if (instancia == null) {
			instancia = new FamiliaService();
		}
		return instancia;
	}

	public FamiliaService() {
		adicionarFiltro("idFamilia", RestritorHb.RESTRITOR_EQ, "idFamilia");
		adicionarFiltro("idExterno", RestritorHb.RESTRITOR_EQ, "idExterno");
	}

	@Override
	protected Criteria montaCriteria(Session sessao, int join) {
		return sessao.createCriteria(Familia.class);
	}

	/**
	 * Localiza família pelo código Omie ou cria registro em tb_familia.
	 */
	public BigInteger obterOuSalvarPorIdExterno(Session sessao, BigInteger codigoFamiliaOmie, String descricaoFamilia)
			throws Exception {
		if (codigoFamiliaOmie == null) {
			return null;
		}

		Familia filtro = new Familia();
		filtro.setIdExterno(codigoFamiliaOmie);
		Familia existente = this.get(sessao, filtro, 0);
		if (existente != null) {
			return existente.getIdFamilia();
		}

		Familia nova = new Familia();
		nova.setIdExterno(codigoFamiliaOmie);
		nova.setDesFamilia(resolverDescricaoFamilia(descricaoFamilia, codigoFamiliaOmie));
		nova.setFlgIntegral("N");
		sessao.save(nova);
		return nova.getIdFamilia();
	}

	private String resolverDescricaoFamilia(String descricaoFamilia, BigInteger codigoFamiliaOmie) {
		if (descricaoFamilia != null && !descricaoFamilia.trim().isEmpty()) {
			return descricaoFamilia.trim();
		}
		return "Família Omie " + codigoFamiliaOmie;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map restritores() {
		return restritores;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map filtroPropriedade() {
		return filtroPropriedade;
	}
}
