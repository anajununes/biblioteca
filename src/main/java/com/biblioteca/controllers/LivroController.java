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

import com.biblioteca.entities.LivroEntity;
import com.biblioteca.services.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PreAuthorize("hasRole('ADM') OR hasRole ('FUNCIONARIO')")
    @PostMapping ("/salvar")
    public ResponseEntity<LivroEntity> save(@RequestBody LivroEntity livro) {
        return ResponseEntity.ok(livroService.save(livro));
    }

    //@PreAuthorize("hasRole('USUARIO')")
    @GetMapping ("/listar")
    public ResponseEntity<List<LivroEntity>> listAll() {
        return ResponseEntity.ok(livroService.findAll());
    }

    @PreAuthorize("hasRole('ADM') OR hasRole ('FUNCIONARIO')")
    @GetMapping("/localizar/{id}")
    public ResponseEntity<LivroEntity> findById(@PathVariable Long id) {
        LivroEntity livro = livroService.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        return ResponseEntity.ok(livro);
    }

    @PreAuthorize("hasRole('ADM') OR hasRole ('FUNCIONARIO')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<LivroEntity> update(@PathVariable Long id, @RequestBody LivroEntity livro) {
        return livroService.findById(id)
                .map(existingLivro -> {
                    existingLivro.setTitulo(livro.getTitulo());
                    existingLivro.setAutor(livro.getAutor());
                    existingLivro.setGenero(livro.getGenero());
                    existingLivro.setIsbn(livro.getIsbn());
                    existingLivro.setAnoPublicacao(livro.getAnoPublicacao());
                    existingLivro.setQuantExemplares(livro.getQuantExemplares());
                    LivroEntity updatedLivro = livroService.save(existingLivro);
                    return ResponseEntity.ok(updatedLivro);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADM')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

