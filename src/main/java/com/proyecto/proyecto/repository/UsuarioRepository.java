package com.proyecto.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    
    //Métodos de busqueda para verificar si ya existen en la base de datos 
    //exists: Verifica si existe
    
    //Username
    boolean existsByUsername(String username);

    //Correo
    boolean existsByCorreo(String correo);

    //RUT 
    boolean existsByNumRunAndDvRun(String numRun, String dvRun);

    //Telefono
    boolean existsByTelefono(String telefono);


    //Método recuperación de cuenta
    //Optional: Clase para representar un valor que puede o no ser nulo
    //findBy: busca y retorna el objeto
    Optional<Usuario> findByCorreo(String correo);



}
