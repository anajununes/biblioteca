package com.biblioteca.controllers;

import java.util.List;

public record UsuarioRequestExisting (
		
		Long idUsuario,
		String nomeUsuario,
		String cpf,
		String senha,
		List<Long> roles	//pq uma lista de regras?
							//vai o tipo do usuario?
		
) {
	
}
