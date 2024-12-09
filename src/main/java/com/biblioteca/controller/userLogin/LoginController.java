package com.biblioteca.controller.userLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.biblioteca.services.UserLoginService;


@RestController
@RequestMapping ("/")
@CrossOrigin ("*")
public class LoginController {
	
	@Autowired
	public UserLoginService userLoginService;
	
    @PostMapping("login")
    public ResponseEntity<String> logar(@RequestBody LoginRequest login) {
        try {
            return ResponseEntity.ok(userLoginService.logar(login));
        }catch(AuthenticationException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("novo-usuario/save")
    public ResponseEntity<HttpStatus> saveNewUser(@RequestParam String nomeUsuario,@RequestParam String cpf, @RequestParam String senha){
        userLoginService.saveNewUser(nomeUsuario, cpf, senha);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
