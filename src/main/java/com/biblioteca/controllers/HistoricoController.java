package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.HistoricoEntity;
import com.biblioteca.services.HistoricoService;

@RestController
@RequestMapping("/historicos")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<HistoricoEntity>> listarHistorico(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(historicoService.listarHistorico(idUsuario));
    }
}

