package com.biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.controller.userLogin.LoginRequest;
import com.biblioteca.entities.UsuarioEntity;
import com.biblioteca.repositories.UsuarioRepository;


@Service
public class UserLoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtUtils jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;


    public String logar(LoginRequest login) {
        var data = usuarioRepository.findByNomeUsuario(login.loginUsuario()).get();
        System.out.println(data.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.loginUsuario(),
                        login.senhaUsuario()
                )
        );
        UsuarioEntity user = usuarioRepository.findByNomeUsuario(login.loginUsuario()).get();
        String jwtToken = jwtService.generateToken(user);

        return jwtToken;
    }

    public void saveNewUser(String nomeUsuario, String cpf, String senha) {
    	usuarioRepository.save(
                new UsuarioEntity(
                        null,
                        nomeUsuario,
                        cpf,
                        passwordEncoder.encode(senha),
                        UsuarioEntity.TipoUsuario.USUARIO                        )
        );
    }

}
