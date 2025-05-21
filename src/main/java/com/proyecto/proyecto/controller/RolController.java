package com.proyecto.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto.model.Rol;
import com.proyecto.proyecto.service.RolService;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/cliente")
    public Rol obtenerRolCliente(){
        return rolService.obtenerRolCliente();
    }

}
