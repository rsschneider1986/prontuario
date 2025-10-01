package com.prontuario.dao;

import com.prontuario.entity.Medicamento;
import com.prontuario.entity.Receita;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class ReceitaDAO extends GenericDAO<Receita> {
    public ReceitaDAO() { super(Receita.class); }

    public List<Receita> findByPacienteMedicamentoPaged(String pacienteNome, String medicamentoNome, int first, int pageSize) {
        String ql = "select r from Receita r join r.medicamentos mr join mr.medicamento m join r.paciente p where lower(p.nome) like :pn and lower(m.nome) like :mn order by r.id";
        TypedQuery<Receita> q = em.createQuery(ql, Receita.class);
        q.setParameter("pn", "%" + pacienteNome.toLowerCase() + "%");
        q.setParameter("mn", "%" + medicamentoNome.toLowerCase() + "%");
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    public Long countByPacienteMedicamento(String pacienteNome, String medicamentoNome) {
        String ql = "select count(distinct r) from Receita r join r.medicamentos mr join mr.medicamento m join r.paciente p where lower(p.nome) like :pn and lower(m.nome) like :mn";
        TypedQuery<Long> q = em.createQuery(ql, Long.class);
        q.setParameter("pn", "%" + pacienteNome.toLowerCase() + "%");
        q.setParameter("mn", "%" + medicamentoNome.toLowerCase() + "%");
        return q.getSingleResult();
    }   
}
