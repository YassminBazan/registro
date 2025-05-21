package com.proyecto.proyecto.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.model.Rol;
import com.proyecto.proyecto.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Rol obtenerRolCliente() {
        return rolRepository.findByNombreRol("CLIENTE")
        .orElseThrow(() -> new RuntimeException("El rol CLIENTE no está registrado"));
    }

}
