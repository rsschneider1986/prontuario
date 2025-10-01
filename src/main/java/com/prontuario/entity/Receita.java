package com.prontuario.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receita")
public class Receita implements Serializable {
	private static final long serialVersionUID = -6757563311494421457L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "paciente_id")
	private Paciente paciente;

	@OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<MedicamentoReceitado> medicamentos = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<MedicamentoReceitado> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(List<MedicamentoReceitado> medicamentos) {
		this.medicamentos = medicamentos;
	}
}
