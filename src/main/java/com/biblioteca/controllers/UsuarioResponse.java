package com.biblioteca.controllers;

public record UsuarioResponse (
		Long idUsuario,
		String nomeUsuario,
		String cpf,
		String senha
							//vai o tipo do usuario?
) {
	
}