package com.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.entities.EmprestimoEntity;

public interface EmprestimoRepository extends JpaRepository<EmprestimoEntity, Long> {
}