package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioEntity> save(@RequestBody UsuarioEntity usuarioEntity) {
        return ResponseEntity.ok(usuarioService.saveUser(usuarioEntity));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioEntity>> listAll() {
        return ResponseEntity.ok(usuarioService.listAllUsers());
    }

    @GetMapping("/localizar/{id}")
    public ResponseEntity<UsuarioEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findUserById(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioEntity> update(@PathVariable Long id, @RequestBody UsuarioEntity usuario) {
        return ResponseEntity.ok(usuarioService.updateUser(id, usuario));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
