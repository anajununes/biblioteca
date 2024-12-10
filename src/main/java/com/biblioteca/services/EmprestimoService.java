package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.EmprestimoEntity;
import com.biblioteca.entities.LivroEntity;
import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.repositories.EmprestimoRepository;
import com.biblioteca.repositories.LivroRepository;
import com.biblioteca.repositories.UsuarioRepository;

@Service
public class EmprestimoService {
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private MultaService multaService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private HistoricoService historicoService;
    
    @Autowired
    private LivroRepository livroRepository;

    public EmprestimoEntity realizarEmprestimo(EmprestimoEntity emprestimo) {
        UsuarioEntity usuario = usuarioRepository.findById(emprestimo.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        LivroEntity livro = livroRepository.findById(emprestimo.getLivro().getIdLivro())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (livro.getQuantExemplares() <= 0) {
            throw new RuntimeException("Não há exemplares disponíveis para empréstimo");
        }

        // Verifica se a data de empréstimo foi definida pelo usuário
        if (emprestimo.getDataEmprestimo() == null) {
            throw new IllegalArgumentException("A data do empréstimo deve ser informada.");
        }

        // Calcula a data de devolução se ainda não foi definida
        if (emprestimo.getDataDevolucao() == null) {
            emprestimo.setDataDevolucao(emprestimo.calcularDataDevolucao(emprestimo.getDataEmprestimo(), 7));
        }

        // Atualiza o estoque do livro
        livro.setQuantExemplares(livro.getQuantExemplares() - 1);
        livroRepository.save(livro);

        // Define o usuário e o livro no empréstimo e salva
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        EmprestimoEntity salvo = emprestimoRepository.save(emprestimo);

        // Registra o histórico do empréstimo
       /* historicoService.registrarHistorico(salvo, null);*/

        return salvo;
    }
    
    public List<EmprestimoEntity> findAll() {
        List<EmprestimoEntity> emprestimos = emprestimoRepository.findAll();
        emprestimos.forEach(this::verificarStatusECalcularMulta);
        return emprestimos;
    }
    
    public EmprestimoEntity atualizarEmprestimo(Long id, EmprestimoEntity emprestimoAtualizado) {
        EmprestimoEntity emprestimoExistente = emprestimoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        emprestimoExistente.setDataDevolucao(emprestimoAtualizado.getDataDevolucao());
        emprestimoExistente.setStatusEmprestimo(emprestimoAtualizado.getStatusEmprestimo());

        return emprestimoRepository.save(emprestimoExistente);
    }


    public Optional<EmprestimoEntity> findById(Long id) {
        Optional<EmprestimoEntity> emprestimo = emprestimoRepository.findById(id);
        emprestimo.ifPresent(this::verificarStatusECalcularMulta);
        return emprestimo;
    }
    
    public EmprestimoEntity realizarDevolucao(Long idEmprestimo) {
        EmprestimoEntity emprestimo = emprestimoRepository.findById(idEmprestimo)
            .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if ("Devolvido".equals(emprestimo.getStatusEmprestimo())) {
            throw new RuntimeException("Este empréstimo já foi devolvido");
        }

        emprestimo.realizarDevolucao();
        
        LivroEntity livro = emprestimo.getLivro();
        livro.setQuantExemplares(livro.getQuantExemplares() + 1);
        livroRepository.save(livro);

        if (emprestimo.getDataDevolucaoEfetiva().isAfter(emprestimo.getDataDevolucao())) {
            multaService.calcularMulta(emprestimo);
        }

        EmprestimoEntity emprestimoAtualizado = emprestimoRepository.save(emprestimo);
        historicoService.atualizarHistorico(emprestimoAtualizado);

        return emprestimoAtualizado;
    }

    public void deleteById(Long id) {
        emprestimoRepository.deleteById(id);
    }

    private void verificarStatusECalcularMulta(EmprestimoEntity emprestimo) {
        emprestimo.verificarStatus();
        if ("Atrasado".equals(emprestimo.getStatusEmprestimo())) {
            multaService.calcularMulta(emprestimo);
        }
    }
}
