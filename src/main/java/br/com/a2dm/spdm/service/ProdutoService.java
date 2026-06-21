package br.com.a2dm.spdm.service;

import static br.com.a2dm.brcmn.domain.OmieCaracteristicaProduto.LOTE_MINIMO;
import static br.com.a2dm.brcmn.domain.OmieCaracteristicaProduto.QTD_MULTIPLO;

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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.domain.OmieCaracteristicaProduto;
import br.com.a2dm.brcmn.dto.ProdutoDTO;
import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.spdm.entity.Familia;
import br.com.a2dm.spdm.entity.PedidoProduto;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.entity.Receita;
import br.com.a2dm.spdm.omie.repository.OmieProdutosRepository;
import br.com.a2dm.spdm.omie.service.OmieProdutoEstruturaService;
import br.com.a2dm.spdm.omie.service.OmieProdutoService;

public class ProdutoService extends A2DMHbNgc<Produto>
{
	private static ProdutoService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_RECEITA = 4;
	
	public static final int JOIN_CLIENTE_PRODUTO = 8;
	
	private JSFUtil util = new JSFUtil();
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static ProdutoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new ProdutoService();
		}
		return instancia;
	}
	
	public ProdutoService()
	{
		adicionarFiltro("idProduto", RestritorHb.RESTRITOR_EQ,"idProduto");
		adicionarFiltro("idProduto", RestritorHb.RESTRITOR_NE, "filtroMap.idProdutoNotEq");
		adicionarFiltro("desProduto", RestritorHb.RESTRITOR_LIKE, "desProduto");
		adicionarFiltro("desProduto", RestritorHb.RESTRITOR_EQ, "filtroMap.desProduto");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
		adicionarFiltro("idReceita", RestritorHb.RESTRITOR_EQ, "idReceita");
		adicionarFiltro("idExterno", RestritorHb.RESTRITOR_EQ, "idExterno");
		adicionarFiltro("listaClienteProduto.flgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.flgAtivoClienteProduto");
		adicionarFiltro("listaClienteProduto.idCliente", RestritorHb.RESTRITOR_EQ, "filtroMap.idCliente");
	}
	
	@Override
	protected void validarInserir(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setFlgAtivo("S");
		produto.setFiltroMap(new HashMap<String, Object>());
		produto.getFiltroMap().put("desProduto", vo.getDesProduto().trim());
		
		produto = this.get(sessao, produto, 0);
		
		if(produto != null)
		{
			throw new Exception("Este produto já está cadastrado na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setFiltroMap(new HashMap<String, Object>());
		produto.getFiltroMap().put("idProdutoNotEq", vo.getIdProduto());
		produto.getFiltroMap().put("desProduto", vo.getDesProduto().trim());
		produto.setFlgAtivo(vo.getFlgAtivo());		
		
		produto = this.get(sessao, produto, 0);
		
		if(produto != null)
		{
			throw new Exception("Este produto já está cadastrado na sua base de dados!");
		}
	}
	
	public Produto inativar(Produto vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = inativar(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			vo.setFlgAtivo("S");
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	public Produto inativar(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setIdProduto(vo.getIdProduto());
		produto = this.get(sessao, produto, 0);
				
		vo.setFlgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Produto ativar(Produto vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = ativar(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			vo.setFlgAtivo("N");
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	public Produto ativar(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setIdProduto(vo.getIdProduto());
		produto = this.get(sessao, produto, 0);
		
		vo.setFlgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	public List<Produto> pesquisarProducaoDia(Produto produto) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		try
		{
			return this.pesquisarProducaoDia(sessao, produto);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> pesquisarProducaoDia(Session sessao, Produto produto) throws Exception
	{		
		Criteria criteria = sessao.createCriteria(Produto.class);
		
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.groupProperty("idProduto"));
		projection.add(Projections.groupProperty("desProduto"));
		projection.add(Projections.groupProperty("qtdMassaCrua"));
		projection.add(Projections.groupProperty("pedido.datPedido"));
		projection.add(Projections.groupProperty("receita.desReceita"));
		projection.add(Projections.sum("listaPedidoProduto.qtdSolicitada"));
		
		criteria.createAlias("listaPedidoProduto", "listaPedidoProduto");
		criteria.createAlias("listaPedidoProduto.pedido", "pedido");
		criteria.createAlias("receita", "receita");
		
		criteria.add(Restrictions.eq("pedido.flgAtivo", "S"));
		criteria.add(Restrictions.eq("listaPedidoProduto.flgAtivo", "S"));
		criteria.add(Restrictions.eq("pedido.datPedido", produto.getDatPedido()));
		
		criteria.addOrder(Order.asc("receita.desReceita"));
		
		criteria.setProjection(projection);
		List<Object[]> resultado = criteria.list();		
		List<Produto> retorno = new ArrayList<Produto>(5);
		
		if (resultado != null && resultado.size() > 0)
	    {
	    	int j = 0;
	    	for (int i = 0; i < resultado.size(); i++)
	    	{
	    		j = 0;
	    		
	    		Produto produtoResult = new Produto();
	    		produtoResult.setReceita(new Receita());
	    		produtoResult.setIdProduto((BigInteger) resultado.get(i)[j++]);
	    		produtoResult.setDesProduto((String) resultado.get(i)[j++]);
	    		BigInteger qtdMassaCrua = (BigInteger) resultado.get(i)[j++];
	    		produtoResult.setQtdMassaCrua(qtdMassaCrua != null ? qtdMassaCrua : BigInteger.ZERO);
	    		produtoResult.setDatPedido((Date) resultado.get(i)[j++]);
	    		produtoResult.getReceita().setDesReceita((String) resultado.get(i)[j++]);
	    		BigInteger qtdSolicitada = (BigInteger) resultado.get(i)[j++];
	    		produtoResult.setQtdSolicitada(qtdSolicitada != null ? qtdSolicitada : BigInteger.ZERO);
	    		
	            retorno.add(produtoResult);
	    	}
	    }
		
		this.atualizarPrioridadeProducaoDia(sessao, retorno, produto.getDatPedido());
	      
	    return retorno;
	}

	public List<Produto> pesquisarProducaoDiaConsolidado(Produto produto) throws Exception {
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		try {
			return pesquisarProducaoDiaConsolidado(sessao, produto);
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> pesquisarProducaoDiaConsolidado(Session sessao, Produto produto) throws Exception {
		Criteria criteria = sessao.createCriteria(PedidoProduto.class, "pp");

		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.groupProperty("familia.idFamilia"));
		projection.add(Projections.groupProperty("familia.desFamilia"));
		projection.add(Projections.groupProperty("produto.idProduto"));
		projection.add(Projections.groupProperty("produto.desProduto"));
		projection.add(Projections.groupProperty("produto.flgIntegral"));
		projection.add(Projections.groupProperty("pp.unidade"));
		projection.add(Projections.sum("pp.qtdSolicitada"));

		criteria.createAlias("pp.pedido", "pedido");
		criteria.createAlias("pp.produto", "produto");
		criteria.createAlias("pp.familia", "familia", JoinType.LEFT_OUTER_JOIN);

		criteria.add(Restrictions.eq("pedido.flgAtivo", "S"));
		criteria.add(Restrictions.eq("pp.flgAtivo", "S"));
		criteria.add(Restrictions.eq("pedido.datPedido", produto.getDatPedido()));

		criteria.addOrder(Order.asc("familia.desFamilia"));
		criteria.addOrder(Order.asc("produto.desProduto"));

		criteria.setProjection(projection);
		List<Object[]> resultado = criteria.list();
		List<Produto> retorno = new ArrayList<>();

		if (resultado != null) {
			for (Object[] row : resultado) {
				int j = 0;
				Produto item = new Produto();
				item.setFamilia(new Familia());
				item.getFamilia().setIdFamilia((BigInteger) row[j++]);
				item.getFamilia().setDesFamilia((String) row[j++]);
				item.setIdProduto((BigInteger) row[j++]);
				item.setDesProduto((String) row[j++]);
				item.setFlgIntegral((String) row[j++]);
				item.setUnidade((String) row[j++]);
				item.setQtdSolicitada((BigInteger) row[j++]);
				retorno.add(item);
			}
		}
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	private void atualizarPrioridadeProducaoDia(Session sessao, List<Produto> lista, Date dataPedido) throws Exception
	{
		Criteria criteria = sessao.createCriteria(Produto.class);
		
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.groupProperty("idProduto"));		
		projection.add(Projections.groupProperty("cliente.numPrioridade"));		
		projection.add(Projections.sum("listaPedidoProduto.qtdSolicitada"));
		
		criteria.createAlias("listaPedidoProduto", "listaPedidoProduto");
		criteria.createAlias("listaPedidoProduto.pedido", "pedido");
		criteria.createAlias("pedido.cliente", "cliente");
		
		criteria.add(Restrictions.eq("pedido.flgAtivo", "S"));
		criteria.add(Restrictions.eq("listaPedidoProduto.flgAtivo", "S"));
		criteria.add(Restrictions.eq("pedido.datPedido", dataPedido));
		
		criteria.addOrder(Order.asc("idProduto"));
		criteria.addOrder(Order.asc("cliente.numPrioridade"));
		
		criteria.setProjection(projection);
		List<Object[]> resultado = criteria.list();		
		List<Produto> retorno = new ArrayList<Produto>(5);
		
		if (resultado != null && resultado.size() > 0)
	    {
	    	int j = 0;
	    	for (int i = 0; i < resultado.size(); i++)
	    	{
	    		j = 0;
	    		
	    		Produto produtoResult = new Produto();
	    		
	    		produtoResult.setIdProduto((BigInteger) resultado.get(i)[j++]);
	    		produtoResult.setNumPrioridade((BigInteger) resultado.get(i)[j++]);
	    		produtoResult.setQtdSolicitada((BigInteger) resultado.get(i)[j++]);
	    		
	            retorno.add(produtoResult);
	    	}
	    }
		
		//MESCLANDO AS LISTAS PARA ATUALIZAR AS PRIORIDADES DE CADA PRODUTO
		for (Produto produto : lista)
		{
			produto.setPrioridade1(new BigInteger("0"));
			produto.setPrioridade2(new BigInteger("0"));
			produto.setPrioridade3(new BigInteger("0"));
			produto.setPrioridade4(new BigInteger("0"));
			
			for (Produto objPrioridade : retorno)
			{
				if(produto.getIdProduto().longValue() == objPrioridade.getIdProduto().longValue())
				{
					if (objPrioridade.getNumPrioridade() != null && objPrioridade.getNumPrioridade().intValue() > 0)
					{
						if(objPrioridade.getNumPrioridade().intValue() == 1)
						{
							produto.setPrioridade1(objPrioridade.getQtdSolicitada());
						}
						
						if(objPrioridade.getNumPrioridade().intValue() == 2)
						{
							produto.setPrioridade2(objPrioridade.getQtdSolicitada());
						}
						
						if(objPrioridade.getNumPrioridade().intValue() == 3)
						{
							produto.setPrioridade3(objPrioridade.getQtdSolicitada());
						}
						
						if(objPrioridade.getNumPrioridade().intValue() == 4)
						{
							produto.setPrioridade4(objPrioridade.getQtdSolicitada());
						}
					}
				}
			}
		}
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Produto.class);
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
	         criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_RECEITA) != 0)
	    {
			criteria.createAlias("receita", "receita");
	    }
		
		if ((join & JOIN_CLIENTE_PRODUTO) != 0)
	    {
			criteria.createAlias("listaClienteProduto", "listaClienteProduto");
	    }
		
		return criteria;
	}
		
	@Override
	protected void setarOrdenacao(Criteria criteria, Produto vo, int join)
	{
		criteria.addOrder(Order.asc("desProduto"));
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map restritores() 
	{
		return restritores;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map filtroPropriedade() 
	{
		return filtroPropriedade;
	}
	
	public void atualizarIdExterno(Produto produto) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			atualizarIdExterno(sessao, produto);
			tx.commit();
		}
		catch (Exception e)
		{
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	/**
	 * Resolve produto local a partir do código Omie (id_externo) ou da PK local (id_produto).
	 */
	public Produto obterProdutoLocalPorCodigoOmie(Session sessao, BigInteger codigoProdutoOmie) throws Exception {
		if (codigoProdutoOmie == null) {
			return null;
		}

		Produto filtroPk = new Produto();
		filtroPk.setIdProduto(codigoProdutoOmie);
		Produto produto = this.get(sessao, filtroPk, 0);
		if (produto != null) {
			return produto;
		}

		Produto filtroExterno = new Produto();
		filtroExterno.setIdExterno(codigoProdutoOmie);
		filtroExterno.setFlgAtivo("S");
		return this.get(sessao, filtroExterno, 0);
	}

	/**
	 * Sincroniza um produto recebido via webhook da Omie (inclusão, alteração ou inativação lógica).
	 * Retorna a ação local executada (constantes de WebhookLog).
	 */
	public String sincronizarDoWebhook(BigInteger codigoOmie, String descricao, String unidade, Double valorUnitario,
			BigInteger codigoFamiliaOmie, boolean inativar, BigInteger idUsuario) throws Exception {
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try {
			Produto produto = obterProdutoLocalPorCodigoOmie(sessao, codigoOmie);
			String acao;

			if (produto == null) {
				if (inativar) {
					tx.commit();
					return "INATIVACAO";
				}

				ProdutoDTO dados = new ProdutoDTO();
				dados.setIdProduto(codigoOmie);
				dados.setDesProduto(descricao);
				dados.setUnidade(unidade);
				dados.setValorUnitario(valorUnitario);
				dados.setCodigoFamiliaOmie(codigoFamiliaOmie);

				obterOuSincronizarProdutoLocal(sessao, dados, idUsuario);
				acao = "INCLUSAO";
			} else {
				if (inativar) {
					produto.setFlgAtivo("N");
					acao = "INATIVACAO";
				} else {
					if (descricao != null && !descricao.trim().isEmpty()) {
						produto.setDesProduto(descricao.trim());
					}
					produto.setFlgAtivo("S");
					acao = "ALTERACAO";
				}

				produto.setIdExterno(codigoOmie);
				produto.setFlgSinc("S");
				produto.setIdUsuarioAlt(idUsuario);
				produto.setDatAlteracao(new Date());
				sessao.merge(produto);
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
	 * Busca produto local ou importa da Omie e grava em tb_produto quando não existir.
	 */
	public Produto obterOuSincronizarProdutoLocal(Session sessao, ProdutoDTO dadosPedido, BigInteger idUsuario)
			throws Exception {
		if (dadosPedido == null || dadosPedido.getIdProduto() == null) {
			throw new Exception("Código do produto Omie não informado.");
		}

		Produto produto = obterProdutoLocalPorCodigoOmie(sessao, dadosPedido.getIdProduto());
		if (produto != null) {
			return produto;
		}

		return inserirProdutoFromOmie(sessao, dadosPedido, idUsuario);
	}

	private Produto inserirProdutoFromOmie(Session sessao, ProdutoDTO dadosPedido, BigInteger idUsuario) throws Exception {
		BigInteger codigoOmie = dadosPedido.getIdProduto();
		ProdutoDTO dadosOmie = null;

		try {
			dadosOmie = OmieProdutoService.getInstance().consultarProduto(codigoOmie);
		} catch (Exception e) {
			System.out.println("Aviso: não foi possível consultar produto " + codigoOmie + " na Omie: " + e.getMessage());
		}

		ProdutoDTO merged = mergeProdutoDto(dadosPedido, dadosOmie);
		enriquecerCaracteristicasOmie(merged);
		enriquecerUnidadeOmie(merged);

		Produto produto = new Produto();
		produto.setIdExterno(codigoOmie);
		produto.setDesProduto(resolverDescricaoProduto(merged, codigoOmie));
		produto.setFlgIntegral(resolverFlgIntegral(produto.getDesProduto()));
		produto.setIdReceita(obterReceitaPadrao(sessao));
		produto.setQtdLoteMinimo(merged.getQtdLoteMinimo() != null ? merged.getQtdLoteMinimo() : BigInteger.ONE);
		produto.setQtdMultiplo(merged.getQtdMultiplo() != null ? merged.getQtdMultiplo() : BigInteger.ONE);
		produto.setQtdMassaCrua(BigInteger.ZERO);
		produto.setFlgAtivo("S");
		produto.setFlgSinc("S");
		produto.setDatCadastro(new Date());
		produto.setIdUsuarioCad(idUsuario);
		produto.setIdFamilia(FamiliaService.getInstancia().obterOuSalvarPorIdExterno(sessao,
				merged.getCodigoFamiliaOmie(), merged.getDescricaoFamilia()));

		sessao.save(produto);
		return produto;
	}

	private ProdutoDTO mergeProdutoDto(ProdutoDTO dadosPedido, ProdutoDTO dadosOmie) {
		ProdutoDTO merged = dadosOmie != null ? dadosOmie : new ProdutoDTO();
		merged.setIdProduto(dadosPedido.getIdProduto());

		if (dadosPedido.getDesProduto() != null && !dadosPedido.getDesProduto().trim().isEmpty()) {
			merged.setDesProduto(dadosPedido.getDesProduto());
		}
		if (dadosPedido.getQtdLoteMinimo() != null) {
			merged.setQtdLoteMinimo(dadosPedido.getQtdLoteMinimo());
		}
		if (dadosPedido.getQtdMultiplo() != null) {
			merged.setQtdMultiplo(dadosPedido.getQtdMultiplo());
		}
		if (dadosPedido.getUnidade() != null && !dadosPedido.getUnidade().trim().isEmpty()) {
			merged.setUnidade(dadosPedido.getUnidade());
		}
		if (dadosPedido.getValorUnitario() != null) {
			merged.setValorUnitario(dadosPedido.getValorUnitario());
		}
		if (dadosPedido.getCodigoFamiliaOmie() != null) {
			merged.setCodigoFamiliaOmie(dadosPedido.getCodigoFamiliaOmie());
		}
		if (dadosPedido.getDescricaoFamilia() != null && !dadosPedido.getDescricaoFamilia().trim().isEmpty()) {
			merged.setDescricaoFamilia(dadosPedido.getDescricaoFamilia());
		}
		return merged;
	}

	private void enriquecerCaracteristicasOmie(ProdutoDTO produtoDTO) throws Exception {
		Map<String, OmieCaracteristicaProduto> caracteristicas = OmieProdutosRepository.getInstance()
				.obterCaracteristicasProduto(produtoDTO.getIdProduto());
		if (caracteristicas == null || caracteristicas.isEmpty()) {
			return;
		}

		OmieCaracteristicaProduto loteMinimo = caracteristicas.get(LOTE_MINIMO.toLowerCase());
		if (loteMinimo != null && produtoDTO.getQtdLoteMinimo() == null) {
			produtoDTO.setQtdLoteMinimo(loteMinimo.conteudoToBigInteger());
		}

		OmieCaracteristicaProduto qtdeMultiplo = caracteristicas.get(QTD_MULTIPLO.toLowerCase());
		if (qtdeMultiplo != null && produtoDTO.getQtdMultiplo() == null) {
			produtoDTO.setQtdMultiplo(qtdeMultiplo.conteudoToBigInteger());
		}
	}

	private void enriquecerUnidadeOmie(ProdutoDTO produtoDTO) {
		if (produtoDTO.getUnidade() != null && !produtoDTO.getUnidade().trim().isEmpty()) {
			return;
		}
		try {
			String unidade = OmieProdutoEstruturaService.getInstance()
					.obterProdutoEstrutura(produtoDTO.getIdProduto()).getIdentDTO().getUnidProduto();
			if (unidade != null && !unidade.trim().isEmpty()) {
				produtoDTO.setUnidade(unidade);
			}
		} catch (Exception e) {
			System.out.println("Aviso: não foi possível obter unidade do produto " + produtoDTO.getIdProduto()
					+ " na Omie: " + e.getMessage());
		}
	}

	private String resolverDescricaoProduto(ProdutoDTO produtoDTO, BigInteger codigoOmie) {
		if (produtoDTO.getDesProduto() != null && !produtoDTO.getDesProduto().trim().isEmpty()) {
			return produtoDTO.getDesProduto().trim();
		}
		return "Produto Omie " + codigoOmie;
	}

	private String resolverFlgIntegral(String desProduto) {
		if (desProduto != null && desProduto.toLowerCase().contains("integral")) {
			return "S";
		}
		return "N";
	}

	private BigInteger obterReceitaPadrao(Session sessao) throws Exception {
		Criteria criteria = sessao.createCriteria(Receita.class);
		criteria.add(Restrictions.eq("flgAtivo", "S"));
		criteria.addOrder(Order.asc("idReceita"));
		criteria.setMaxResults(1);
		Receita receita = (Receita) criteria.uniqueResult();
		if (receita == null) {
			throw new Exception("Não há receita ativa cadastrada para importar o produto da Omie.");
		}
		return receita.getIdReceita();
	}

	public void atualizarIdExterno(Session sessao, Produto produto) throws Exception 
	{
		Produto produtoBD = new Produto();
		produtoBD.setIdProduto(produto.getIdProduto());
		
		produtoBD = super.get(sessao, produtoBD, 0);
		
		if (produtoBD != null) 
		{
			produtoBD.setIdExterno(produto.getIdExterno());
			produtoBD.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
			produtoBD.setDatAlteracao(new Date());
			
			super.alterar(sessao, produtoBD);
		}
	}
}
