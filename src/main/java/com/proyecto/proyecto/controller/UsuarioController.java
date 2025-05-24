package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.service.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            Map<String, Object> response = new HashMap<>();
            response.put("Mensaje", "Usuario " + nuevousuario.getUsername() + " registrado con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }

    //Endpoint para buscar
    @GetMapping("/buscar/{correo}")
    public ResponseEntity<?> obtenerUsuarioPorCorreo(@PathVariable String correo){
        return UsuarioService.buscarPorCorreo(correo)
        .map(usuario-> {

            //Solo datos necesarios 
            Map<String, Object> response = new HashMap<>();
            response.put("idUsuario", usuario.getIdUsuario());
            response.put("correo", usuario.getCorreo());
            response.put("passwordHash", usuario.getPasswordHash());
            response.put("estado", usuario.getEstado());

            //asigna un solo rol por usuario
            response.put("rol", usuario.getRoles().get(0));

            return ResponseEntity.ok(response);
        })
        .orElse(ResponseEntity.notFound().build());
    }

    //Endpoint para cambiar password en registro
    //@RequestBody --> datos enviados por el cliente a la
    @PutMapping("/{id}/actualizar-password")
    public ResponseEntity<?> actualizarPassword(@PathVariable Long id, @RequestBody Map<String, String> body){
        String nuevaPassword = body.get("nuevaPassword");
        String nuevoHash = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
        boolean actualizada = UsuarioService.actualizarPassword(id, nuevoHash);
        if (actualizada) {
            return ResponseEntity.ok("Contraseña actualizada");
        }else{
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }
}
