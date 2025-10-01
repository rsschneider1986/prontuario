package com.prontuario.dao;

import com.prontuario.entity.Paciente;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class PacienteDAO extends GenericDAO<Paciente> {
    public PacienteDAO() { super(Paciente.class); }

    public List<Paciente> findByNomeCpfPaged(String termo, int first, int pageSize) {
        String ql = "select p from Paciente p where lower(p.nome) like :t or p.cpf like :t order by p.nome";
        TypedQuery<Paciente> q = em.createQuery(ql, Paciente.class);
        q.setParameter("t", "%" + termo.toLowerCase() + "%");
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    public Long countByNomeCpf(String termo) {
        String ql = "select count(p) from Paciente p where lower(p.nome) like :t or p.cpf like :t";
        TypedQuery<Long> q = em.createQuery(ql, Long.class);
        q.setParameter("t", "%" + termo.toLowerCase() + "%");
        return q.getSingleResult();
    }
}
