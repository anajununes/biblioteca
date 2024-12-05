package com.biblioteca.controllers;

import java.util.List;

public record UsuarioRequest (
		Long idUsuario,
		String nomeUsuario,
		String cpf,
		String senha,
		List<Long> roles
) {
	
}
