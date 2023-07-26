package br.com.a2dm.spdm.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.NaoConformidade;

public class NaoConformidadeService extends A2DMHbNgc<NaoConformidade>
{
	
	private static NaoConformidadeService instancia = null;
	
	public static final int JOIN_CLIENTE = 1;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static NaoConformidadeService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new NaoConformidadeService();
		}
		return instancia;
	}
	
	public NaoConformidadeService()
	{	
		adicionarFiltro("idCliente", RestritorHb.RESTRITOR_EQ, "idCliente");
		adicionarFiltro("datCadastro", RestritorHb.RESTRITOR_EQ, "datCadastro");
	}
	
	@Override
	public NaoConformidade inserir(NaoConformidade vo) throws Exception {
		System.out.println("");
		vo.setDatAlteracao(Calendar.getInstance().getTime());
		return super.inserir(vo);
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(NaoConformidade.class);
		
		if ((join & JOIN_CLIENTE) != 0)
	    {
			criteria.createAlias("cliente", "cliente");
	    }
		
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
	protected void setarOrdenacao(Criteria criteria, NaoConformidade vo, int join)
	{
		criteria.addOrder(Order.asc("datCadastro"));
	}
}