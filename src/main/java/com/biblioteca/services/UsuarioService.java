package com.biblioteca.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity saveUser(UsuarioEntity usuarioEntity) {
        return usuarioRepository.save(usuarioEntity);
    }

    public List<UsuarioEntity> listAllUsers() {
        return usuarioRepository.findAll();
    }

    public UsuarioEntity findUserById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deleteUser(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    public UsuarioEntity updateUser(Long idUsuario, UsuarioEntity newUser) {
        UsuarioEntity existingUser = findUserById(idUsuario);
        existingUser.setNomeUsuario(newUser.getNomeUsuario());
        existingUser.setCpf(newUser.getCpf());
        existingUser.setSenha(newUser.getSenha());
        existingUser.setTipoUsuario(newUser.getTipoUsuario());
        return usuarioRepository.save(existingUser);
    }
}
