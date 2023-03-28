package br.com.a2dm.spdm.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
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
}