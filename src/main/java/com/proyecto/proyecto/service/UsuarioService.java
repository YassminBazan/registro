package com.proyecto.proyecto.service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.model.Rol;
import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolService rolService;

    @Transactional
    public Usuario registrarUsuario(Usuario usuario){

        //usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        //Validar que no este registrado
        //Username
        if(usuarioRepository.existsByUsername(usuario.getUsername())){
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }
        //Correo
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ingresado ya está registrado");
        }
        //RUT
        if(usuarioRepository.existsByNumRunAndDvRun(usuario.getNumRun(), usuario.getDvRun())){
            throw new IllegalArgumentException("El RUT ingresado ya está registrado");
        }
        //Telefono es diferente porque no es un campo obligatorio
        if(usuario.getTelefono() != null && usuarioRepository.existsByTelefono(usuario.getTelefono())){
            throw new IllegalArgumentException("El telefono ingresado ya está registrado");
        }


        //Asigna rol cliente por defecto
        //Collections.si.... se encarga de crear una lista con un solo 
        Rol rolCliente = rolService.obtenerRolCliente();
        usuario.setRoles(Collections.singletonList(rolCliente));

        //Método que registra la fecha de registro y el estado por defecto
        usuario.crearCuenta();

        //Guarda el usuario en la base de datos con el save
        return usuarioRepository.save(usuario);
    }
}
