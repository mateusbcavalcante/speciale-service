package br.com.a2dm.spdm.service;

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
import br.com.a2dm.spdm.entity.Aviso;

public class AvisoService extends A2DMHbNgc<Aviso>{
	
	private static AvisoService instancia = null;
	
	public static AvisoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new AvisoService();
		}
		return instancia;
	}
	
	public AvisoService()
	{
		adicionarFiltro("id_aviso", RestritorHb.RESTRITOR_EQ, "id_aviso");
		adicionarFiltro("dat_aviso", RestritorHb.RESTRITOR_EQ, "dat_aviso");
	}
	
	public Aviso ativar(Aviso vo) throws Exception
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
			vo.setAtivo(false);
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	public Aviso ativar(Session sessao, Aviso vo) throws Exception
	{
		Aviso aviso = new Aviso();
		aviso.setId_aviso(vo.getId_aviso());
		aviso = super.get(sessao, aviso, 0);
		
		vo.setAtivo(true);
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	public Aviso inativar(Aviso vo) throws Exception
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
			vo.setAtivo(false);
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	public Aviso inativar(Session sessao, Aviso vo) throws Exception
	{
		Aviso aviso = new Aviso();
		aviso.setId_aviso(vo.getId_aviso());
		aviso = super.get(sessao, aviso, 0);
		
		vo.setAtivo(false);
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	

	@Override
	protected Criteria montaCriteria(Session sessao, int join) {
		Criteria criteria = sessao.createCriteria(Aviso.class);
		return criteria;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map restritores() {
		// TODO Auto-generated method stub
		return restritores;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map filtroPropriedade() {
		// TODO Auto-generated method stub
		return filtroPropriedade;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Aviso vo, int join)
	{
		criteria.addOrder(Order.asc("dat_aviso"));
	}

}
