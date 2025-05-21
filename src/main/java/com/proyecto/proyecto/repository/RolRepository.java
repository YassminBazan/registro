package com.proyecto.proyecto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto.model.Rol;

public interface RolRepository extends JpaRepository <Rol, Long>{
  
    //Busca el Nombre del rol 
    Optional<Rol> findByNombreRol(String nombreRol);
    
    //Método de busqueda 
    boolean existsByNombreRol(String nombreRol);

}
