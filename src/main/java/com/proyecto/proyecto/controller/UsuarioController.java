package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.service.*;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService UsuarioService;

    //Endpoint para registrar un nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario){
        try {
            Usuario nuevousuario = UsuarioService.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevousuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }


}
