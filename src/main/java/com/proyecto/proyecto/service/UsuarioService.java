package com.proyecto.proyecto.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.model.Rol;
import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    //@Autowired inyecta dependencias automaticamente 
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

        //Encriptacion de contraseña
            String hash = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
            usuario.setPasswordHash(hash);


        //Guarda el usuario en la base de datos con el save
        return usuarioRepository.save(usuario);
    }

    //Método para buscar por correo
    public Optional<Usuario> buscarPorCorreo(String correo){
        return usuarioRepository.findByCorreo(correo);
    }

    //Método para actualizar contraseña
    public boolean actualizarPassword(Long id, String nuevaPassword){
        try {
            Optional<Usuario> opt = usuarioRepository.findById(id);
            if(opt.isPresent()){
                Usuario u = opt.get();
                u.setPasswordHash(nuevaPassword);
                usuarioRepository.save(u);
                return true;
            }
            return false;
            
        } catch (Exception e) {
            System.out.println("Error al actualizar usuario");
            e.printStackTrace();
            return false;
        }
   
    }
}
