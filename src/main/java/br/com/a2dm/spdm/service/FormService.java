package br.com.a2dm.spdm.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.a2dm.brcmn.entity.ativmob.Form;
import br.com.a2dm.brcmn.util.A2DMHbNgc;

public class FormService extends A2DMHbNgc<Form>
{
	private static FormService instancia = null;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static FormService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new FormService();
		}
		return instancia;
	}
	
	public FormService()
	{
		
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Form.class);
		
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