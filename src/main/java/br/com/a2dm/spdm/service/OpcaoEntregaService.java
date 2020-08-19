package br.com.a2dm.spdm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.spdm.entity.OpcaoEntrega;

public class OpcaoEntregaService extends A2DMHbNgc<OpcaoEntrega>
{
	private static OpcaoEntregaService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int OPCAO_ENTREGA = 2;
	
	private JSFUtil util = new JSFUtil();
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static OpcaoEntregaService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new OpcaoEntregaService();
		}
		return instancia;
	}
	
	public OpcaoEntregaService()
	{
		adicionarFiltro("idOpcaoEntrega", RestritorHb.RESTRITOR_EQ,"idOpcaoEntrega");
		adicionarFiltro("desOpcaoEntrega", RestritorHb.RESTRITOR_LIKE, "desOpcaoEntrega");
		adicionarFiltro("desOpcaoEntrega", RestritorHb.RESTRITOR_EQ, "filtroMap.desOpcaoEntrega");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
	}
	
	@Override
	protected void validarInserir(Session sessao, OpcaoEntrega vo) throws Exception
	{
		OpcaoEntrega opcaoEntrega = new OpcaoEntrega();
		opcaoEntrega.setFlgAtivo("S");
		opcaoEntrega.setFiltroMap(new HashMap<String, Object>());
		opcaoEntrega.getFiltroMap().put("desOpcaoEntrega", vo.getDesOpcaoEntrega().trim());		
		
		opcaoEntrega = this.get(sessao, opcaoEntrega, 0);
		
		if(opcaoEntrega != null)
		{
			throw new Exception("Esta opção de entrega já está cadastrada na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, OpcaoEntrega vo) throws Exception
	{
		OpcaoEntrega opcaoEntrega = new OpcaoEntrega();
		opcaoEntrega.setFiltroMap(new HashMap<String, Object>());
		opcaoEntrega.getFiltroMap().put("idOpcaoEntregaNotEq", vo.getIdOpcaoEntrega());
		opcaoEntrega.getFiltroMap().put("desOpcaoEntrega", vo.getDesOpcaoEntrega().trim());
		opcaoEntrega.setFlgAtivo(vo.getFlgAtivo());		
		
		opcaoEntrega = this.get(sessao, opcaoEntrega, 0);
		
		if(opcaoEntrega != null)
		{
			throw new Exception("Esta opção de entrega já está cadastrada na sua base de dados!");
		}
	}
	
	public OpcaoEntrega inativar(OpcaoEntrega vo) throws Exception
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

	public OpcaoEntrega inativar(Session sessao, OpcaoEntrega vo) throws Exception
	{
		OpcaoEntrega opcaoEntrega = new OpcaoEntrega();
		opcaoEntrega.setIdOpcaoEntrega(vo.getIdOpcaoEntrega());
		opcaoEntrega = this.get(sessao, opcaoEntrega, 0);
				
		vo.setFlgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public OpcaoEntrega ativar(OpcaoEntrega vo) throws Exception
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
	
	public OpcaoEntrega ativar(Session sessao, OpcaoEntrega vo) throws Exception
	{
		OpcaoEntrega opcaoEntrega = new OpcaoEntrega();
		opcaoEntrega.setIdOpcaoEntrega(vo.getIdOpcaoEntrega());
		opcaoEntrega = this.get(sessao, opcaoEntrega, 0);
		
		vo.setFlgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(OpcaoEntrega.class);
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
	         criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		return criteria;
	}
		
	@Override
	protected void setarOrdenacao(Criteria criteria, OpcaoEntrega vo, int join)
	{
		criteria.addOrder(Order.asc("desOpcaoEntrega"));
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
	
	public void atualizarIdExterno(OpcaoEntrega opcaoEntrega) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			atualizarIdExterno(sessao, opcaoEntrega);
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

	public void atualizarIdExterno(Session sessao, OpcaoEntrega opcaoEntrega) throws Exception 
	{
		OpcaoEntrega opcaoEntregaBD = new OpcaoEntrega();
		opcaoEntregaBD.setIdOpcaoEntrega(opcaoEntrega.getIdOpcaoEntrega());
		
		opcaoEntregaBD = super.get(sessao, opcaoEntregaBD, 0);
		
		if (opcaoEntregaBD != null) 
		{
			opcaoEntregaBD.setIdExterno(opcaoEntrega.getIdExterno());
			opcaoEntregaBD.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
			opcaoEntregaBD.setDatAlteracao(new Date());
			
			super.alterar(sessao, opcaoEntregaBD);
		}
	}
}
