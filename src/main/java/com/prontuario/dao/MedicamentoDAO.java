package com.prontuario.dao;

import com.prontuario.entity.Medicamento;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class MedicamentoDAO extends GenericDAO<Medicamento> {
    public MedicamentoDAO() { super(Medicamento.class); }

    public List<Medicamento> findByNomePaged(String termo, int first, int pageSize) {
        String ql = "select m from Medicamento m where lower(m.nome) like :t order by m.nome";
        TypedQuery<Medicamento> q = em.createQuery(ql, Medicamento.class);
        q.setParameter("t", "%" + termo.toLowerCase() + "%");
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    public Long countByNome(String termo) {
        String ql = "select count(m) from Medicamento m where lower(m.nome) like :t";
        TypedQuery<Long> q = em.createQuery(ql, Long.class);
        q.setParameter("t", "%" + termo.toLowerCase() + "%");
        return q.getSingleResult();
    }
}
