package com.biblioteca.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.HistoricoEntity;

@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoEntity, Long> {

    List<HistoricoEntity> findByUsuario_IdUsuario(Long idUsuario);
    Optional<HistoricoEntity> findByEmprestimo_IdEmprestimo(Long idEmprestimo);
}
