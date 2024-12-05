package com.biblioteca.controllers;

import com.biblioteca.entities.UsuarioEntity;


public class UsuarioMapper {

	public static UsuarioResponse toResponse(UsuarioEntity usuarioResponse){
        return new UsuarioResponse(
                usuarioResponse.getIdUsuario(),
                usuarioResponse.getNomeUsuario(),
                usuarioResponse.getCpf(),
                usuarioResponse.getSenha()

        );
    }
	
    public static UsuarioEntity toEntityFromRequest(UsuarioRequest usuarioRequest){
        return new UsuarioEntity(
                null,//id
                usuarioRequest.nomeUsuario(),
                usuarioRequest.cpf(),
                usuarioRequest.senha(),
                null				// o que fazer com o tipo usuario?
        );
    }
    
    public static UsuarioEntity toEntityFromRequestExisting(UsuarioRequestExisting usuarioRequest){
        return new UsuarioEntity(
                usuarioRequest.idUsuario(),
                usuarioRequest.nomeUsuario(),
                usuarioRequest.cpf(),
                usuarioRequest.senha(),
                null				//o que fazer com o tipo usuaeio?
               
        );
    }
    
    

}
