package com.prontuario.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.prontuario.entity.Medicamento;

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
    
	public void removerMedicamento(Medicamento medicamento) {
	    Long medicamentoId = medicamento.getId();
	    
	    // Primeiro, remove as referências nas tabelas relacionadas
	    Query query = em.createQuery("UPDATE MedicamentoReceitado mr SET mr.medicamento = null WHERE mr.medicamento.id = :medicamentoId");
	    query.setParameter("medicamentoId", medicamentoId);
	    query.executeUpdate();
	    
	    // Agora exclui o medicamento
	    Medicamento managed = em.find(Medicamento.class, medicamentoId);
	    if (managed != null) {
	        em.remove(managed);
	    }
	}
}
