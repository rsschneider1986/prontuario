package com.prontuario.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicamento")
public class Medicamento implements Serializable {
	private static final long serialVersionUID = 6618304182265033317L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String nome;

	@OneToMany(mappedBy = "medicamento")
	private List<MedicamentoReceitado> prescricoes = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<MedicamentoReceitado> getPrescricoes() {
		return prescricoes;
	}

	public void setPrescricoes(List<MedicamentoReceitado> prescricoes) {
		this.prescricoes = prescricoes;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Medicamento)) return false;
	    Medicamento other = (Medicamento) o;
	    return id != null && id.equals(other.getId());
	}

	@Override
	public int hashCode() {
	    return 31;
	}

}
