package br.com.a2dm.spdm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.Item;

public class ItemService extends A2DMHbNgc<Item>
{
	
	public static final String OBJECT_CAPTURE_IMAGE = "CAPTURE UMA IMAGEM";
	
	private static ItemService instancia = null;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static ItemService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new ItemService();
		}
		return instancia;
	}
	
	public ItemService()
	{
		adicionarFiltro("idItem", RestritorHb.RESTRITOR_EQ, "idItem");
		adicionarFiltro("idSugestaoPedido", RestritorHb.RESTRITOR_EQ, "idSugestaoPedido");
		adicionarFiltro("integId", RestritorHb.RESTRITOR_EQ, "integId");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Item.class);
		
		return criteria;
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
	
	@Override
	protected void validarInserir(Session sessao, Item vo) throws Exception {
		List<Item> itemSearch = this.pesquisar(vo, 0);
		
		if (itemSearch.size() > 0) {
			throw new Exception("Este produto já está cadastrado na sua base de dados!");
		}
	}
	
	public Item atualizarQuantidade(Item vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		
		try
		{
			vo = atualizarQuantidade(sessao, vo);
			tx.commit();
			return vo;
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

	public Item atualizarQuantidade(Session sessao, Item item) throws Exception {
		Item itemFind = new Item();
		
		itemFind = get(sessao, item, 0);
		
		itemFind.setValue(item.getValue());
		
		sessao.merge(itemFind);
		return itemFind;
	}

	public Item removerItem(Item vo) throws Exception {
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		
		try
		{
			vo = removerItem(sessao, vo);
			tx.commit();
			return vo;
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
	
	public Item removerItem(Session sessao, Item item) throws Exception {
		item = get(sessao, item, 0);
		sessao.delete(item);
		return item;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Item vo, int join)
	{
		criteria.addOrder(Order.asc("label"));
	}
}