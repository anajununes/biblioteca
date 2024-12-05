package com.biblioteca.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.biblioteca.entities.UserLoginEntity;

public interface UserLoginRepository extends JpaRepository<UserLoginEntity, Long>{

    @Query(value = "select * from usuario where username = ?1", nativeQuery = true)
    Optional<UserLoginEntity> findByUsername(String login);
}
