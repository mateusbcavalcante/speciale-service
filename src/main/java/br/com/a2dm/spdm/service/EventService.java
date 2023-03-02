package br.com.a2dm.spdm.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.entity.ativmob.Event;
import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;

public class EventService extends A2DMHbNgc<Event>
{
	
	public static final int JOIN_FORM = 1;
	
	private static EventService instancia = null;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static EventService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new EventService();
		}
		return instancia;
	}
	
	public EventService()
	{
		adicionarFiltro("event_id", RestritorHb.RESTRITOR_EQ, "event_id");
		adicionarFiltro("event_dth", RestritorHb.RESTRITOR_EQ, "event_dth");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Event.class);
		
		if ((join & JOIN_FORM) != 0)
	    {
			criteria.createAlias("forms", "forms", JoinType.LEFT_OUTER_JOIN);
	    }
		
		return criteria;
	}
	
	public Event aprovar(Event vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = aprovar(sessao, vo);
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
	
	public Event aprovar(Session sessao, Event vo) throws Exception
	{	
		vo.setStatus("Aprovado");
		sessao.merge(vo);
		
		return vo;
	}
	
	public Event reprovar(Event vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = reprovar(sessao, vo);
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
	
	public Event reprovar(Session sessao, Event vo) throws Exception
	{
		vo.setStatus("Reprovado");
		sessao.merge(vo);
		
		return vo;
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
	protected void setarOrdenacao(Criteria criteria, Event vo, int join)
	{
		criteria.addOrder(Order.asc("event_dth"));
	}
}