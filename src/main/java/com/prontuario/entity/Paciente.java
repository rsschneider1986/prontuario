package com.prontuario.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
	private static final long serialVersionUID = 5049474682291842214L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false, unique = true)
	private String cpf;

	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Receita> receitas = new ArrayList<>();

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Paciente))
			return false;
		Paciente other = (Paciente) o;
		return id != null && id.equals(other.getId());
	}

	@Override
	public int hashCode() {
		return 31;
	}
}
