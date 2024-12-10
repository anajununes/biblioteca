package com.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.MultaEntity;
import com.biblioteca.services.EmprestimoService;
import com.biblioteca.services.MultaService;

@RestController
@RequestMapping("/multas")
public class MultaController {

    @Autowired
    private MultaService multaService;
    
    @Autowired
    private EmprestimoService emprestimoService;
    
    @PreAuthorize("hasRole('ADM') OR hasRole ('FUNCIONARIO')")
    @PostMapping("/calcular/{idEmprestimo}")
    public ResponseEntity<MultaEntity> calcularMulta(@PathVariable Long idEmprestimo) {
        EmprestimoEntity emprestimo = emprestimoService.findById(idEmprestimo)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
        MultaEntity multaEntity = multaService.calcularMulta(emprestimo);
        
        MultaEntity multa = new MultaEntity();
        multa.setValorMulta(multaEntity.getValorMulta());
        return ResponseEntity.ok(multa);
    }
    
    @PreAuthorize("hasRole('ADM')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<MultaEntity> atualizarMulta(@PathVariable Long id, @RequestBody MultaEntity multaAtualizada) {
        try {
            MultaEntity multa = multaService.atualizarMulta(id, multaAtualizada);
            return ResponseEntity.ok(multa);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

}
