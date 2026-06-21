package br.com.a2dm.spdm.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.TabelaPreco;
import br.com.a2dm.spdm.omie.payload.TabelaPrecoPayload;
import br.com.a2dm.spdm.omie.service.OmieTabelaPrecoService;

public class TabelaPrecoService extends A2DMHbNgc<TabelaPreco> {

	private static TabelaPrecoService instancia = null;

	public static final int JOIN_ITENS = 1;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();

	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();

	public static TabelaPrecoService getInstancia() {
		if (instancia == null) {
			instancia = new TabelaPrecoService();
		}
		return instancia;
	}

	public TabelaPrecoService() {
		adicionarFiltro("idTabelaPreco", RestritorHb.RESTRITOR_EQ, "idTabelaPreco");
		adicionarFiltro("idExterno", RestritorHb.RESTRITOR_EQ, "idExterno");
		adicionarFiltro("codTabela", RestritorHb.RESTRITOR_EQ, "codTabela");
		adicionarFiltro("nomTabela", RestritorHb.RESTRITOR_LIKE, "nomTabela");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
	}

	/**
	 * Sincroniza uma tabela de preço recebida via webhook da Omie (inclusão, alteração ou inativação lógica).
	 * Retorna a ação local executada (constantes de WebhookLog).
	 */
	public String sincronizarDoWebhook(BigInteger nCodTabPreco, String codTabela, String nomTabela, String origem,
			Double perAcrescimo, Double perDesconto, boolean inativar, BigInteger idUsuario) throws Exception {
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try {
			TabelaPreco tabela = obterTabelaLocalPorCodigoOmie(sessao, nCodTabPreco);
			String acao;

			if (tabela == null) {
				if (inativar) {
					tx.commit();
					return "INATIVACAO";
				}

				tabela = new TabelaPreco();
				tabela.setIdExterno(nCodTabPreco);
				tabela.setDatCadastro(new Date());
				tabela.setIdUsuarioCad(idUsuario);
				preencherDados(tabela, codTabela, nomTabela, origem, perAcrescimo, perDesconto);
				tabela.setFlgAtivo("S");
				tabela.setFlgSinc("S");
				sessao.save(tabela);
				acao = "INCLUSAO";
			} else {
				if (inativar) {
					tabela.setFlgAtivo("N");
					acao = "INATIVACAO";
				} else {
					preencherDados(tabela, codTabela, nomTabela, origem, perAcrescimo, perDesconto);
					tabela.setFlgAtivo("S");
					acao = "ALTERACAO";
				}
				tabela.setFlgSinc("S");
				tabela.setIdUsuarioAlt(idUsuario);
				tabela.setDatAlteracao(new Date());
				sessao.merge(tabela);
			}

			tx.commit();
			return acao;
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			sessao.close();
		}
	}

	/**
	 * Localiza a tabela de preço local pelo código Omie ou busca/cria a partir da Omie quando ausente.
	 * Retorna a PK local (id_tabela_preco).
	 */
	public BigInteger obterOuSincronizarTabelaLocal(Session sessao, BigInteger nCodTabPreco, BigInteger idUsuario)
			throws Exception {
		if (nCodTabPreco == null) {
			return null;
		}

		TabelaPreco tabela = obterTabelaLocalPorCodigoOmie(sessao, nCodTabPreco);
		if (tabela != null) {
			return tabela.getIdTabelaPreco();
		}

		TabelaPrecoPayload dadosOmie = null;
		try {
			dadosOmie = OmieTabelaPrecoService.getInstance().obterTabelaPrecoPorCodigo(nCodTabPreco);
		} catch (Exception e) {
			System.out.println("Aviso: não foi possível consultar a tabela de preço " + nCodTabPreco
					+ " na Omie: " + e.getMessage());
		}

		TabelaPreco nova = new TabelaPreco();
		nova.setIdExterno(nCodTabPreco);
		if (dadosOmie != null) {
			preencherDados(nova, dadosOmie.getcCodigo(), dadosOmie.getcNome(), dadosOmie.getcOrigem(),
					dadosOmie.getnPercAcrescimo(), dadosOmie.getnPercDesconto());
			nova.setFlgAtivo(dadosOmie.isInativa() ? "N" : "S");
		} else {
			preencherDados(nova, null, null, null, null, null);
			nova.setFlgAtivo("S");
		}
		nova.setFlgSinc("S");
		nova.setDatCadastro(new Date());
		nova.setIdUsuarioCad(idUsuario);
		sessao.save(nova);
		return nova.getIdTabelaPreco();
	}

	private TabelaPreco obterTabelaLocalPorCodigoOmie(Session sessao, BigInteger nCodTabPreco) throws Exception {
		TabelaPreco filtro = new TabelaPreco();
		filtro.setIdExterno(nCodTabPreco);
		return this.get(sessao, filtro, 0);
	}

	private void preencherDados(TabelaPreco tabela, String codTabela, String nomTabela, String origem,
			Double perAcrescimo, Double perDesconto) {
		if (codTabela != null && !codTabela.trim().isEmpty()) {
			tabela.setCodTabela(codTabela.trim());
		}
		if (nomTabela != null && !nomTabela.trim().isEmpty()) {
			tabela.setNomTabela(nomTabela.trim());
		}
		if (origem != null && !origem.trim().isEmpty()) {
			tabela.setDesOrigem(origem.trim());
		}
		if (perAcrescimo != null) {
			tabela.setPerAcrescimo(perAcrescimo);
		}
		if (perDesconto != null) {
			tabela.setPerDesconto(perDesconto);
		}
	}

	@Override
	protected Criteria montaCriteria(Session sessao, int join) {
		Criteria criteria = sessao.createCriteria(TabelaPreco.class);

		if ((join & JOIN_ITENS) != 0) {
			criteria.createAlias("listaTabelaPrecoItem", "listaTabelaPrecoItem");
		}

		return criteria;
	}

	@Override
	protected void setarOrdenacao(Criteria criteria, TabelaPreco vo, int join) {
		criteria.addOrder(Order.asc("nomTabela"));
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
