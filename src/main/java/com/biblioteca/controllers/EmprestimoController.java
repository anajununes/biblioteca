package com.biblioteca.controllers;

import java.util.List;
import java.util.Optional;

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

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.services.EmprestimoService;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;
    
    @PostMapping("/realizar")
    public ResponseEntity<EmprestimoEntity> realizarEmprestimo(@RequestBody EmprestimoEntity emprestimo) {
        try {
            EmprestimoEntity salvo = emprestimoService.realizarEmprestimo(emprestimo);
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<EmprestimoEntity> atualizarEmprestimo(@PathVariable Long id, @RequestBody EmprestimoEntity emprestimoAtualizado) {
        try {
            EmprestimoEntity emprestimo = emprestimoService.atualizarEmprestimo(id, emprestimoAtualizado);
            return ResponseEntity.ok(emprestimo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping ("/listar")
    public ResponseEntity<List<EmprestimoEntity>> listarEmprestimos() {
        try {
            List<EmprestimoEntity> emprestimos = emprestimoService.findAll();
            return ResponseEntity.ok(emprestimos);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/devolver/{id}")
    public ResponseEntity<EmprestimoEntity> realizarDevolucao(@PathVariable Long id) {
        try {
            EmprestimoEntity devolvido = emprestimoService.realizarDevolucao(id);
            return ResponseEntity.ok(devolvido);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/localizar")
    public ResponseEntity<List<EmprestimoEntity>> getAllEmprestimos() {
        try {
            List<EmprestimoEntity> emprestimos = emprestimoService.findAll();
            return ResponseEntity.ok(emprestimos);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/localizar/{id}")
    public ResponseEntity<EmprestimoEntity> getEmprestimoById(@PathVariable Long id) {
        try {
            Optional<EmprestimoEntity> emprestimo = emprestimoService.findById(id);
            if (emprestimo.isPresent()) {
                return ResponseEntity.ok(emprestimo.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteEmprestimo(@PathVariable Long id) {
        try {
            emprestimoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}