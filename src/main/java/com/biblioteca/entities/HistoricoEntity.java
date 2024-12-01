package com.biblioteca.entities;

//import java.sql.Date;
import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "historico")
public class HistoricoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorico;
    
    @Column(name = "dataEmprestimo", nullable = false)
    private ZonedDateTime dataEmprestimo;

    @Column(name = "dataDevolucao")
    private ZonedDateTime dataDevolucao;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioEntity usuario;
    
    @ManyToOne
    @JoinColumn(name = "idLivro", nullable = false)
    private LivroEntity livro;
    
    @ManyToOne
    @JoinColumn(name = "idEmprestimo", nullable = false)
    private EmprestimoEntity emprestimo;
    
    @ManyToOne
    @JoinColumn(name = "idDevolucao")
    private EmprestimoEntity devolucao;
    
}
