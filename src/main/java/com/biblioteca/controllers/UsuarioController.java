package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADM') OR hasRole ('FUNCIONARIO')")
    @PostMapping("/salvar")
    public ResponseEntity<UsuarioEntity> save(@RequestBody UsuarioEntity usuarioEntity) {
        return ResponseEntity.ok(usuarioService.saveUser(usuarioEntity));
    }

    @PreAuthorize("hasRole('ADM') OR hasRole ('FUNCIONARIO')")
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioEntity>> listAll() {
        return ResponseEntity.ok(usuarioService.listAllUsers());
    }

    @PreAuthorize("hasRole('ADM') OR hasRole ('FUNCIONARIO')")
    @GetMapping("/localizar/{id}")
    public ResponseEntity<UsuarioEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findUserById(id));
    }

    @PreAuthorize("hasRole('ADM')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioEntity> update(@PathVariable Long id, @RequestBody UsuarioEntity usuario) {
        return ResponseEntity.ok(usuarioService.updateUser(id, usuario));
    }

    @PreAuthorize("hasRole('ADM')")
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
