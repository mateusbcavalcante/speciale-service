package br.com.a2dm.spdm.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
		itemFind.setIdItem(item.getIdItem());
		
		itemFind = get(sessao, itemFind, 0);
		
		itemFind.setValue(item.getValue());
		
		sessao.merge(itemFind);
		return itemFind;
	}	
}