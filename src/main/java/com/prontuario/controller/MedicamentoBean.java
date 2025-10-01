package com.prontuario.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.prontuario.entity.Medicamento;
import com.prontuario.service.ProntuarioService;

@Named
@ViewScoped
public class MedicamentoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4581525431167025987L;

	@Inject
	private ProntuarioService service;

	private LazyDataModel<Medicamento> lazyModel;
	private String termo;
	private Medicamento medicamento = new Medicamento();

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModel<Medicamento>() {

			private static final long serialVersionUID = 7471967528838156267L;

			@Override
			public List<Medicamento> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				List<Medicamento> data = service.buscarMedicamentoPorNomePaged(termo, first, pageSize);
				this.setRowCount(service.countMedicamentoByNome(termo).intValue());
				return data;
			}

		};
	}

	public String novo() {
		medicamento = new Medicamento();
		return "medicamentoForm.xhtml?faces-redirect=true";
	}

	public String editar(Medicamento m) {
		medicamento = m;
		return "medicamentoForm.xhtml?faces-redirect=true";
	}

	@Transactional
	public String salvar() {
		if (medicamento.getId() == null) {
			service.salvarMedicamento(medicamento);
		} else {
			service.removerMedicamento(medicamento);
		}
		return "medicamento.xhtml?faces-redirect=true";
	}

	@Transactional
	public String excluir(Medicamento m) {
		service.removerMedicamento(m);
		return "medicamento.xhtml?faces-redirect=true";
	}

	// getters/setters
	public LazyDataModel<Medicamento> getLazyModel() {
		return lazyModel;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
}
