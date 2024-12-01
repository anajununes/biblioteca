package com.biblioteca.entities;

import java.time.ZoneId;
import java.time.ZonedDateTime;
//import java.time.temporal.ChronoUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emprestimo")
public class EmprestimoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmprestimo;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "idLivro", nullable = false)
    private LivroEntity livro;

    @Column(name = "dataEmprestimo", nullable = false)
    private ZonedDateTime dataEmprestimo;

    @Column(name = "dataDevolucao")
    private ZonedDateTime dataDevolucao;

    @Column(name = "dataDevolucaoEfetiva")
    private ZonedDateTime dataDevolucaoEfetiva;

    @Column(name = "statusEmprestimo")
    private String statusEmprestimo;
    
    @PrePersist
    public void prePersist() {
        this.statusEmprestimo = "Emprestado";
        // A dataEmprestimo deve ser definida externamente, antes da persistência
        if (this.dataEmprestimo == null) {
            throw new IllegalArgumentException("A data do empréstimo deve ser informada.");
        }
        if (this.dataDevolucao == null) {
            this.dataDevolucao = calcularDataDevolucao(this.dataEmprestimo, 7);
        }
    }

    public ZonedDateTime calcularDataDevolucao(ZonedDateTime dataInicial, int dias) {
        ZonedDateTime dataFinal = dataInicial;

        int diasAdicionados = 0;
        while (diasAdicionados < dias) {
            dataFinal = dataFinal.plusDays(1);
            if (dataFinal.getDayOfWeek().getValue() != 6 && dataFinal.getDayOfWeek().getValue() != 7) { // Ignore sábado e domingo
                diasAdicionados++;
            }
        }
        return dataFinal;
    }

    public void verificarStatus() {
        if (this.statusEmprestimo.equals("Devolvido")) {
            return;
        }
        
        ZonedDateTime hoje = ZonedDateTime.now(ZoneId.of("UTC"));
        if (hoje.isAfter(dataDevolucao)) {
            statusEmprestimo = "Atrasado";
        } else {
            statusEmprestimo = "No Prazo";
        }
    }

    public void realizarDevolucao() {
        this.dataDevolucaoEfetiva = ZonedDateTime.now(ZoneId.of("UTC"));
        this.statusEmprestimo = "Devolvido";
    }
}
