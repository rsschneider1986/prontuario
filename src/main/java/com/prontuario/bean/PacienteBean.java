package com.prontuario.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.prontuario.entity.Paciente;
import com.prontuario.service.ProntuarioService;

@Named
@ViewScoped
public class PacienteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7683174074698749756L;

	@Inject
	private ProntuarioService service;

	private LazyDataModel<Paciente> lazyModel;
	private String termo;
	private Paciente paciente = new Paciente();

	@PostConstruct
	public void init() {
		
		Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String idParam = params.get("id");
        
        if (idParam != null && !idParam.isEmpty()) {
            paciente = service.buscarPacientePorId(Long.valueOf(idParam));
        } else {
            paciente = new Paciente();
        }
        
		lazyModel = new LazyDataModel<Paciente>() {
			private static final long serialVersionUID = -4752676587689841891L;

			@Override
			public List<Paciente> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				List<Paciente> data = service.buscarPacientePorNomeCpfPaged(termo, first, pageSize);
				this.setRowCount(service.countPacienteByNomeCpf(termo).intValue());
				return data;
			}
		};
	}

	public String novo() {
		paciente = new Paciente();
		return "pacienteForm.xhtml?faces-redirect=true";
	}

	public String editar(Paciente p) {
		return "pacienteForm.xhtml?faces-redirect=true&id=" + p.getId();
	}

	@Transactional
	public String salvar() {
		if (paciente.getId() == null) {
			service.salvarPaciente(paciente);
		} else {
			service.atualizarPaciente(paciente);
		}
		return "paciente.xhtml?faces-redirect=true";
	}

	@Transactional
	public String excluir(Paciente p) {
		service.removerPaciente(p);
		return "paciente.xhtml?faces-redirect=true";
	}

	// getters/setters
	public LazyDataModel<Paciente> getLazyModel() {
		return lazyModel;
	}

	public String getTermo() {
		return termo;
	}

	public void setTermo(String termo) {
		this.termo = termo;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
}
