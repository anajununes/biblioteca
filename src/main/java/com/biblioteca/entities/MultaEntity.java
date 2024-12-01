package com.biblioteca.entities;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZoneId;
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
@Table(name = "multa")
public class MultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMulta;

    @ManyToOne
    @JoinColumn(name = "idEmprestimo", nullable = false)
    private EmprestimoEntity emprestimo;

    @Column(name = "valorMulta", nullable = false)
    private BigDecimal valorMulta;

    @Column(name = "dataCalculo", nullable = false)
    private ZonedDateTime dataCalculo;

    public void calcularMulta() {
        // Usa a data atual com o fuso horário correto
        ZonedDateTime hoje = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        // Verifica se há atraso comparando com a data de devolução
        if (emprestimo.getDataDevolucaoEfetiva() != null &&
            hoje.isAfter(emprestimo.getDataDevolucaoEfetiva())) {
            
            // Calcula o atraso em dias úteis (baseado na lógica de empréstimo)
            long diasAtraso = Duration.between(emprestimo.getDataDevolucaoEfetiva(), hoje).toDays();
            if (diasAtraso > 0) {
                BigDecimal multaPorDia = new BigDecimal("5.00");
                valorMulta = multaPorDia.multiply(BigDecimal.valueOf(diasAtraso));
                dataCalculo = hoje; // Armazena a data do cálculo
            } else {
                valorMulta = BigDecimal.ZERO;
            }
        } else {
            valorMulta = BigDecimal.ZERO; // Não há multa se não houver atraso
        }
    }
}
