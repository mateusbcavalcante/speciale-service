package br.com.a2dm.spdm.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.dto.PedidoDTO;
import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.SugestaoPedido;
import br.com.a2dm.spdm.omie.service.OmiePedidoService;

public class SugestaoPedidoService extends A2DMHbNgc<SugestaoPedido>
{
	
	public static final int JOIN_FORM = 1;
	
	public static final int JOIN_PEDIDO_OPCAO_ENTREGA = 2;
	
	// public static final int JOIN_CLIENTE = 4;
	
	private static SugestaoPedidoService instancia = null;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static SugestaoPedidoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new SugestaoPedidoService();
		}
		return instancia;
	}
	
	public SugestaoPedidoService()
	{
		adicionarFiltro("eventId", RestritorHb.RESTRITOR_EQ, "eventId");
		adicionarFiltro("eventDth", RestritorHb.RESTRITOR_EQ, "eventDth");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(SugestaoPedido.class);
		
		
		if ((join & JOIN_FORM) != 0)
	    {
			criteria.createAlias("itens", "itens", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_PEDIDO_OPCAO_ENTREGA) != 0)
	    {
			criteria.createAlias("opcaoEntrega", "opcaoEntrega", JoinType.LEFT_OUTER_JOIN);
	    }
		
		return criteria;
	}
	
	public SugestaoPedido aprovar(SugestaoPedido vo) throws Exception
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
	
	public SugestaoPedido aprovar(Session sessao, SugestaoPedido vo) throws Exception
	{	
		vo.setStatus("Aprovado");
		sessao.merge(vo);
		/*
		PedidoDTO pedidoDTO = this.buildPedidoDto(vo);
		PedidoDTO pedidoDTOCriado = OmiePedidoService.getInstance().cadastrarPedido(pedidoDTO);
		
		if (pedidoDTOCriado != null) {
			vo.setStatus("Aprovado");
			sessao.merge(vo);			
		}
		*/
		return vo;
	}
	
	public PedidoDTO buildPedidoDto(SugestaoPedido vo) {
		PedidoDTO pedidoDTO = new PedidoDTO();
		return pedidoDTO;
	}
	
	public SugestaoPedido reprovar(SugestaoPedido vo) throws Exception
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
	
	public SugestaoPedido reprovar(Session sessao, SugestaoPedido vo) throws Exception
	{
		vo.setItens(null);
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
	protected void setarOrdenacao(Criteria criteria, SugestaoPedido vo, int join)
	{
		criteria.addOrder(Order.asc("eventDth"));
	}
}