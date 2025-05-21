package com.proyecto.proyecto.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Data
@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Column(unique = true, nullable = false)
    private String nombreRol;

    @Column(length = 255, nullable = false)
    private String descripcion;

    //Colección de elementos simples que carga cada Rol con su conjunto de permisos
    @ElementCollection(targetClass = PermisoEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name= "rol_permisos", joinColumns = @JoinColumn(name = "rol_id") )
    @Enumerated(EnumType.STRING)
    //Especifica el nombre e la columna donde va ir el Enum
    @Column(name= "permiso")

    //Set no permite duplicados 
    private Set<PermisoEnum> permisos= new HashSet<>();




}
