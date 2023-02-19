package br.com.a2dm.spdm.ativmob.repository;

import br.com.a2dm.brcmn.entity.ativmob.Event;
import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AtivMobRepository extends A2DMHbNgc<br.com.a2dm.brcmn.entity.ativmob.Event> {

    private static AtivMobRepository instance;

    private static Map restritores = new HashMap();
    private static Map filtroPropriedade = new HashMap();

    private AtivMobRepository() {
    }

    public static AtivMobRepository getInstance() {
        if(instance == null) {
            instance = new AtivMobRepository();
        }
        return instance;
    }

    public void salvarEvents(ArrayList<Event> events) throws AtivMobRepositoryException {
        Session sessao = HibernateUtil.getSession();
        sessao.setFlushMode(FlushMode.COMMIT);
        Transaction tx = sessao.beginTransaction();
        try {
            for (Event event: events) {
                this.inserir(sessao, event);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new AtivMobRepositoryException(e);
        } finally {
            sessao.close();
        }
    }


    @Override
    protected Criteria montaCriteria(Session sessao, int join) {
        Criteria criteria = sessao.createCriteria(br.com.a2dm.brcmn.entity.ativmob.Event.class);
        return criteria;
    }

    @Override
    protected Map restritores() {
        return restritores;
    }

    @Override
    protected Map filtroPropriedade() {
        return filtroPropriedade;
    }
}
