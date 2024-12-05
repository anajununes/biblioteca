package com.biblioteca.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteca.controller.userLogin.LoginRequest;
import com.biblioteca.entities.UserLoginEntity;
import com.biblioteca.repositories.UserLoginRepository;

@Service
public class UserLoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;
    @Autowired
    private JwtUtils jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;


    public String logar(LoginRequest login) {
        var data = userLoginRepository.findByUsername(login.loginUsuario()).get();
        System.out.println(data.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.loginUsuario(),
                        login.senhaUsuario()
                )
        );
        UserLoginEntity user = userLoginRepository.findByUsername(login.loginUsuario()).get();
        String jwtToken = jwtService.generateToken(user);

        return jwtToken;
    }

    public void saveNewUser(String usuario, String password, Boolean isAdmin) {
    	userLoginRepository.save(
                new UserLoginEntity(
                        null,
                        usuario,
                        passwordEncoder.encode(password),
                        (isAdmin?"ADM":"USUARIO") 						// COLOCO O FUNCIONARIO?
                )
        );
    }

}