package com.prontuario.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.prontuario.entity.Medicamento;
import com.prontuario.entity.Paciente;
import com.prontuario.entity.Receita;
import com.prontuario.service.ProntuarioService;

@Named
@ViewScoped
public class ReceitaBean implements Serializable {

	private static final long serialVersionUID = 286389034019120225L;

	@Inject
	private ProntuarioService service;

	private LazyDataModel<Receita> lazyModel;
	private String pacienteTermo;
	private String medicamentoTermo;

	private Receita receita = new Receita();
	private List<Medicamento> medicamentosSelected = new ArrayList<>();
	private List<Medicamento> medicamentosAvailable = new ArrayList<>();
	private List<Paciente> pacientesAvailable;

	private Paciente pacienteSelected;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModel<Receita>() {

			private static final long serialVersionUID = -6772550719175433200L;

			@Override
			public List<Receita> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				List<Receita> data = service.buscarReceitaPorPacienteMedicamentoPaged(
						pacienteTermo == null ? "" : pacienteTermo, medicamentoTermo == null ? "" : medicamentoTermo,
						first, pageSize);
				this.setRowCount(service.countReceitaByPacienteMedicamento(pacienteTermo == null ? "" : pacienteTermo,
						medicamentoTermo == null ? "" : medicamentoTermo).intValue());
				return data;
			}
		};

		// load small list of meds for form selection (could be lazy too)
		medicamentosAvailable = service.buscarMedicamentoPorNomePaged("", 0, 200);
		pacientesAvailable = service.buscarPacientePorNomeCpfPaged("", 0, 200);
	}

	public String novo() {
		receita = new Receita();
		medicamentosSelected = new ArrayList<>();
		pacienteSelected = null;
		return "receitaForm.xhtml?faces-redirect=true";
	}

	public String editar(Receita r) {
		this.receita = r;
		this.medicamentosSelected = new ArrayList<>();
		for (var mr : r.getMedicamentos())
			medicamentosSelected.add(mr.getMedicamento());
		this.pacienteSelected = r.getPaciente();
		return "receitaForm.xhtml?faces-redirect=true";
	}

	@Transactional
	public String salvar() {
		try {
			// Validação mais robusta
			if (pacienteSelected == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Selecione um paciente"));
				return null;
			}

			if (medicamentosSelected == null || medicamentosSelected.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Selecione pelo menos um medicamento"));
				return null;
			}

			receita.setPaciente(pacienteSelected);
			receita.getMedicamentos().clear();

			for (Medicamento m : medicamentosSelected) {
				var mr = new com.prontuario.entity.MedicamentoReceitado();
				mr.setMedicamento(m);
				mr.setReceita(receita);
				receita.getMedicamentos().add(mr);
			}

			if (receita.getId() == null) {
				service.salvarReceita(receita);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Receita salva com sucesso"));
			} else {
				service.atualizarReceita(receita);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Receita atualizada com sucesso"));
			}

			return "receita.xhtml?faces-redirect=true";

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar receita: " + e.getMessage()));
			return null;
		}
	}

	@Transactional
	public String excluir(Receita r) {
		service.removerReceita(r);
		return "receita.xhtml?faces-redirect=true";
	}

	// getters/setters
	public LazyDataModel<Receita> getLazyModel() {
		return lazyModel;
	}

	public String getPacienteTermo() {
		return pacienteTermo;
	}

	public void setPacienteTermo(String pacienteTermo) {
		this.pacienteTermo = pacienteTermo;
	}

	public String getMedicamentoTermo() {
		return medicamentoTermo;
	}

	public void setMedicamentoTermo(String medicamentoTermo) {
		this.medicamentoTermo = medicamentoTermo;
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public List<Medicamento> getMedicamentosSelected() {
		return medicamentosSelected;
	}

	public void setMedicamentosSelected(List<Medicamento> medicamentosSelected) {
		this.medicamentosSelected = medicamentosSelected;
	}

	public List<Medicamento> getMedicamentosAvailable() {
		return medicamentosAvailable;
	}

	public List<Paciente> getPacientesAvailable() {
		return pacientesAvailable;
	}

	public Paciente getPacienteSelected() {
		return pacienteSelected;
	}

	public void setPacienteSelected(Paciente pacienteSelected) {
		this.pacienteSelected = pacienteSelected;
	}
}
