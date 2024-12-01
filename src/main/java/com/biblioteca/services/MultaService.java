package com.biblioteca.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.MultaEntity;
import com.biblioteca.repositories.MultaRepository;

@Service
public class MultaService {

    @Autowired
    private MultaRepository multaRepository;

    public MultaEntity calcularMulta(EmprestimoEntity emprestimo) {
        // Usa ZonedDateTime para garantir que a data atual esteja no fuso horário correto
        ZonedDateTime hoje = ZonedDateTime.now();

        // Verifica se a data de devolução efetiva existe e se houve atraso
        if (emprestimo.getDataDevolucaoEfetiva() != null) {
            // Se a devolução efetiva for após a data prevista de devolução
            if (hoje.isAfter(emprestimo.getDataDevolucao())) {
                long diasAtraso = Duration.between(emprestimo.getDataDevolucao(), hoje).toDays();

                MultaEntity multa = new MultaEntity();
                multa.setEmprestimo(emprestimo);
                multa.setDataCalculo(hoje);

                // Calcula a multa se houver atraso
                if (diasAtraso > 0) {
                    BigDecimal multaPorDia = new BigDecimal("5.00");
                    multa.setValorMulta(multaPorDia.multiply(BigDecimal.valueOf(diasAtraso)));
                } else {
                    multa.setValorMulta(BigDecimal.ZERO);
                }

                return multaRepository.save(multa);
            }
        }

        // Caso não haja atraso ou a devolução efetiva ainda não tenha ocorrido
        return null;
    }

    public MultaEntity atualizarMulta(Long id, MultaEntity multaAtualizada) {
        MultaEntity multaExistente = multaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Multa não encontrada"));

        multaExistente.setValorMulta(multaAtualizada.getValorMulta());
        multaExistente.setDataCalculo(multaAtualizada.getDataCalculo());

        return multaRepository.save(multaExistente);
    }
}
