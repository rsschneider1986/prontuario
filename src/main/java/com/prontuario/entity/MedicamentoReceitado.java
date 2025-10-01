package com.prontuario.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "medicamento_receitado")
public class MedicamentoReceitado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Receita getReceita() { return receita; }
    public void setReceita(Receita receita) { this.receita = receita; }
    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
}
