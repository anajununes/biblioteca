package com.biblioteca.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.biblioteca.entities.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	public Optional<UsuarioEntity> findByNomeUsuario(String nomeUsuario);
	//public Optional<UsuarioEntity> findByUsername(String login);
	//List <UsuarioEntity> findByUsername (String username);
}
