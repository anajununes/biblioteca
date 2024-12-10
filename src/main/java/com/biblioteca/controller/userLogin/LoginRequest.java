package com.biblioteca.controller.userLogin;

public record LoginRequest (
        String loginUsuario,
        String senhaUsuario
) {
}