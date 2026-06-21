package br.com.a2dm.spdm.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.dto.ProdutoDTO;
import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.entity.TabelaPrecoItem;

public class TabelaPrecoItemService extends A2DMHbNgc<TabelaPrecoItem> {

	private static TabelaPrecoItemService instancia = null;

	public static final int JOIN_TABELA_PRECO = 1;

	public static final int JOIN_PRODUTO = 2;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();

	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();

	public static TabelaPrecoItemService getInstancia() {
		if (instancia == null) {
			instancia = new TabelaPrecoItemService();
		}
		return instancia;
	}

	public TabelaPrecoItemService() {
		adicionarFiltro("idTabelaPrecoItem", RestritorHb.RESTRITOR_EQ, "idTabelaPrecoItem");
		adicionarFiltro("idTabelaPreco", RestritorHb.RESTRITOR_EQ, "idTabelaPreco");
		adicionarFiltro("idProduto", RestritorHb.RESTRITOR_EQ, "idProduto");
		adicionarFiltro("idExternoTabela", RestritorHb.RESTRITOR_EQ, "idExternoTabela");
		adicionarFiltro("idExternoProduto", RestritorHb.RESTRITOR_EQ, "idExternoProduto");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
	}

	/**
	 * Sincroniza um item de tabela de preço recebido da Omie (webhook ou carga inicial).
	 * Resolve tabela e produto locais (criando a partir da Omie quando ausentes) e
	 * insere, atualiza ou inativa logicamente o item. Retorna a ação local executada.
	 */
	public String sincronizarDoWebhook(BigInteger nCodTabPreco, BigInteger nCodProd, Double vlrTabela,
			Double vlrOriginal, Double vlrCalculado, Double perAcrescimo, Double perDesconto, boolean inativar,
			BigInteger idUsuario) throws Exception {
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try {
			TabelaPrecoItem item = obterItemLocal(sessao, nCodTabPreco, nCodProd);
			String acao;

			if (item == null) {
				if (inativar) {
					tx.commit();
					return "INATIVACAO";
				}

				item = new TabelaPrecoItem();
				item.setIdExternoTabela(nCodTabPreco);
				item.setIdExternoProduto(nCodProd);
				item.setIdTabelaPreco(TabelaPrecoService.getInstancia()
						.obterOuSincronizarTabelaLocal(sessao, nCodTabPreco, idUsuario));
				item.setIdProduto(resolverProdutoLocal(sessao, nCodProd, idUsuario));
				preencherValores(item, vlrTabela, vlrOriginal, vlrCalculado, perAcrescimo, perDesconto);
				item.setFlgAtivo("S");
				item.setFlgSinc("S");
				item.setDatCadastro(new Date());
				item.setIdUsuarioCad(idUsuario);
				sessao.save(item);
				acao = "INCLUSAO";
			} else {
				if (inativar) {
					item.setFlgAtivo("N");
					acao = "INATIVACAO";
				} else {
					item.setIdTabelaPreco(TabelaPrecoService.getInstancia()
							.obterOuSincronizarTabelaLocal(sessao, nCodTabPreco, idUsuario));
					item.setIdProduto(resolverProdutoLocal(sessao, nCodProd, idUsuario));
					preencherValores(item, vlrTabela, vlrOriginal, vlrCalculado, perAcrescimo, perDesconto);
					item.setFlgAtivo("S");
					acao = "ALTERACAO";
				}
				item.setFlgSinc("S");
				item.setIdUsuarioAlt(idUsuario);
				item.setDatAlteracao(new Date());
				sessao.merge(item);
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
	 * Lista os produtos e preços da tabela de preço do cliente a partir da base local
	 * (sem consultar a Omie). Resolve a tabela pelo código Omie (id_externo) gravado no cliente.
	 */
	public List<ProdutoDTO> listarProdutosPorClienteLocal(BigInteger idCliente) throws Exception {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(idCliente);
		cliente = ClienteService.getInstancia().get(cliente, 0);

		if (cliente == null || cliente.getIdTabelaPrecoOmie() == null) {
			return new ArrayList<>();
		}

		return listarProdutosPorTabelaPrecoLocal(cliente.getIdTabelaPrecoOmie());
	}

	/**
	 * Lista os produtos e preços de uma tabela de preço (identificada pelo código Omie)
	 * a partir da base local. O idProduto retornado é o código Omie do produto (id_externo),
	 * mantendo a compatibilidade com o envio do pedido à Omie.
	 */
	@SuppressWarnings("unchecked")
	public List<ProdutoDTO> listarProdutosPorTabelaPrecoLocal(BigInteger idExternoTabela) throws Exception {
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		try {
			Criteria criteria = sessao.createCriteria(TabelaPrecoItem.class);
			criteria.createAlias("produto", "produto");
			criteria.add(Restrictions.eq("idExternoTabela", idExternoTabela));
			criteria.add(Restrictions.eq("flgAtivo", "S"));
			criteria.add(Restrictions.eq("produto.flgAtivo", "S"));
			criteria.addOrder(Order.asc("produto.desProduto"));

			List<TabelaPrecoItem> itens = criteria.list();
			List<ProdutoDTO> produtos = new ArrayList<>();

			for (TabelaPrecoItem item : itens) {
				ProdutoDTO produtoDTO = new ProdutoDTO();
				produtoDTO.setIdProduto(item.getIdExternoProduto());
				produtoDTO.setDesProduto(item.getProduto().getDesProduto());
				produtoDTO.setValorUnitario(item.getVlrTabela());
				produtoDTO.setQtdLoteMinimo(item.getProduto().getQtdLoteMinimo());
				produtoDTO.setQtdMultiplo(item.getProduto().getQtdMultiplo());
				produtoDTO.setFlgAtivo("S");
				produtos.add(produtoDTO);
			}

			return produtos;
		} finally {
			sessao.close();
		}
	}

	private TabelaPrecoItem obterItemLocal(Session sessao, BigInteger nCodTabPreco, BigInteger nCodProd)
			throws Exception {
		TabelaPrecoItem filtro = new TabelaPrecoItem();
		filtro.setIdExternoTabela(nCodTabPreco);
		filtro.setIdExternoProduto(nCodProd);
		return this.get(sessao, filtro, 0);
	}

	private BigInteger resolverProdutoLocal(Session sessao, BigInteger nCodProd, BigInteger idUsuario)
			throws Exception {
		Produto produto = ProdutoService.getInstancia().obterProdutoLocalPorCodigoOmie(sessao, nCodProd);
		if (produto != null) {
			return produto.getIdProduto();
		}

		ProdutoDTO dados = new ProdutoDTO();
		dados.setIdProduto(nCodProd);
		produto = ProdutoService.getInstancia().obterOuSincronizarProdutoLocal(sessao, dados, idUsuario);
		return produto.getIdProduto();
	}

	private void preencherValores(TabelaPrecoItem item, Double vlrTabela, Double vlrOriginal, Double vlrCalculado,
			Double perAcrescimo, Double perDesconto) {
		item.setVlrTabela(vlrTabela);
		item.setVlrOriginal(vlrOriginal);
		item.setVlrCalculado(vlrCalculado);
		item.setPerAcrescimo(perAcrescimo);
		item.setPerDesconto(perDesconto);
	}

	@Override
	protected Criteria montaCriteria(Session sessao, int join) {
		Criteria criteria = sessao.createCriteria(TabelaPrecoItem.class);

		if ((join & JOIN_TABELA_PRECO) != 0) {
			criteria.createAlias("tabelaPreco", "tabelaPreco");
		}

		if ((join & JOIN_PRODUTO) != 0) {
			criteria.createAlias("produto", "produto", JoinType.LEFT_OUTER_JOIN);
		}

		return criteria;
	}

	@Override
	protected void setarOrdenacao(Criteria criteria, TabelaPrecoItem vo, int join) {
		criteria.addOrder(Order.asc("idTabelaPrecoItem"));
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
