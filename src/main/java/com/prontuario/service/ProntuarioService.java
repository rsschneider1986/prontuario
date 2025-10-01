package com.prontuario.service;

import com.prontuario.dao.MedicamentoDAO;
import com.prontuario.dao.PacienteDAO;
import com.prontuario.dao.ReceitaDAO;
import com.prontuario.entity.Medicamento;
import com.prontuario.entity.MedicamentoReceitado;
import com.prontuario.entity.Paciente;
import com.prontuario.entity.Receita;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProntuarioService {

	@Inject
	private PacienteDAO pacienteDAO;

	@Inject
	private MedicamentoDAO medicamentoDAO;

	@Inject
	private ReceitaDAO receitaDAO;

	@Transactional
	public Paciente salvarPaciente(Paciente p) {
		return pacienteDAO.save(p);
	}

	@Transactional
	public Paciente atualizarPaciente(Paciente p) {
		return pacienteDAO.update(p);
	}

	@Transactional
	public void removerPaciente(Paciente p) {
		pacienteDAO.delete(p);
	}

	public List<Paciente> buscarPacientePorNomeCpfPaged(String termo, int first, int pageSize) {
		return pacienteDAO.findByNomeCpfPaged(termo == null ? "" : termo, first, pageSize);
	}

	public Long countPacienteByNomeCpf(String termo) {
		return pacienteDAO.countByNomeCpf(termo == null ? "" : termo);
	}

	@Transactional
	public Medicamento salvarMedicamento(Medicamento m) {
		return medicamentoDAO.save(m);
	}

	@Transactional
	public void removerMedicamento(Medicamento m) {
		medicamentoDAO.delete(m);
	}

	public List<Medicamento> buscarMedicamentoPorNomePaged(String termo, int first, int pageSize) {
		return medicamentoDAO.findByNomePaged(termo == null ? "" : termo, first, pageSize);
	}

	public Long countMedicamentoByNome(String termo) {
		return medicamentoDAO.countByNome(termo == null ? "" : termo);
	}

	@Transactional
	public Receita criarReceitaParaPaciente(Paciente paciente, List<Medicamento> medicamentos) {
		Receita r = new Receita();
		r.setPaciente(paciente);
		for (Medicamento m : medicamentos) {
			MedicamentoReceitado mr = new MedicamentoReceitado();
			mr.setMedicamento(m);
			mr.setReceita(r);
			r.getMedicamentos().add(mr);
		}
		paciente.getReceitas().add(r);
		pacienteDAO.update(paciente);
		return r;
	}

	@Transactional
	public Receita salvarReceita(Receita r) {
		return receitaDAO.save(r);
	}

	@Transactional
	public Receita atualizarReceita(Receita r) {
		return receitaDAO.update(r);
	}

	@Transactional
	public void removerReceita(Receita r) {
		receitaDAO.delete(r);
	}

	public List<Receita> buscarReceitaPorPacienteMedicamentoPaged(String pacienteNome, String medicamentoNome,
			int first, int pageSize) {
		return receitaDAO.findByPacienteMedicamentoPaged(pacienteNome == null ? "" : pacienteNome,
				medicamentoNome == null ? "" : medicamentoNome, first, pageSize);
	}

	public Long countReceitaByPacienteMedicamento(String pacienteNome, String medicamentoNome) {
		return receitaDAO.countByPacienteMedicamento(pacienteNome == null ? "" : pacienteNome,
				medicamentoNome == null ? "" : medicamentoNome);
	}

	public Paciente findPacienteById(Long id) {
	    return pacienteDAO.find(id);
	}

	public Medicamento findMedicamentoById(Long id) {
	    return medicamentoDAO.find(id);
	}
}
