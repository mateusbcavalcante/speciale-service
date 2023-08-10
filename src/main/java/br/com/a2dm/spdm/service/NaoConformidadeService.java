package br.com.a2dm.spdm.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import br.com.a2dm.brcmn.entity.Parametro;
import br.com.a2dm.brcmn.service.ParametroService;
import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.spdm.entity.NaoConformidade;

public class NaoConformidadeService extends A2DMHbNgc<NaoConformidade>
{
	private JSFUtil util = new JSFUtil();
	
	private static NaoConformidadeService instancia = null;
	
	public static final int JOIN_CLIENTE = 1;
	
	public static final int JOIN_PRODUTO = 2;

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
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(NaoConformidade.class);
		
		if ((join & JOIN_CLIENTE) != 0)
	    {
			criteria.createAlias("cliente", "cliente");
	    }
				
		return criteria;
	}
	
	public NaoConformidade inativar(NaoConformidade vo) throws Exception {
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
			vo.setAtivo(true);
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	public NaoConformidade inativar(Session sessao, NaoConformidade vo) throws Exception
	{
		NaoConformidade naoConformidade = new NaoConformidade();
		naoConformidade.setIdNaoConformidade(vo.getIdNaoConformidade());
		naoConformidade = this.get(sessao, naoConformidade, 0);
						
		vo.setAtivo(false);
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		sessao.merge(vo);
		return vo;
	}
	
	public NaoConformidade ativar(NaoConformidade vo) throws Exception {
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

	public NaoConformidade ativar(Session sessao, NaoConformidade vo) throws Exception
	{
		NaoConformidade naoConformidade = new NaoConformidade();
		naoConformidade.setIdNaoConformidade(vo.getIdNaoConformidade());
		naoConformidade = this.get(sessao, naoConformidade, 0);
						
		vo.setAtivo(true);
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		sessao.merge(vo);
		return vo;
	}
	
	public NaoConformidade salvarImagem(NaoConformidade vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = salvarImagem(sessao, vo);
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
	
	public NaoConformidade salvarImagem(Session sessao, NaoConformidade vo) throws Exception
	{
		//DIRETORIO DE ARMAZENAMENTO DA IMAGEM
		Parametro parametro = new Parametro();
		parametro.setDescricao("PATH_IMG_NAO_CONFORMIDADE");
		parametro = ParametroService.getInstancia().get(sessao, parametro, 0);
		
		String nomeArquivo = parametro.getValor() + "NC" + vo.getIdNaoConformidade();

		//SALVAR IMAGEM NO DIRETORIO				
		try (InputStream is = vo.getFile().getInputStream();
				OutputStream out = new FileOutputStream(nomeArquivo+".jpg"))
		{
			int read = 0;
			byte[] bytes = new byte[2*1024*1024];

			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		}
		catch (IOException e)
		{
			throw e;
		}
						
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
	protected void setarOrdenacao(Criteria criteria, NaoConformidade vo, int join)
	{
		criteria.addOrder(Order.asc("datCadastro"));
	}
}