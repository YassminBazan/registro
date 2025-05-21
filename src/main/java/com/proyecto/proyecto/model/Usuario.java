package com.proyecto.proyecto.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.*;;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")

public class Usuario {

    //Se declara como Id y se asigna un valor automatico tipo identity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    //RUT
    //Se declara como columna y especifica que sea unico, el largo, que no este vacio
    @NotBlank(message= "El RUT no puede estar vacio")
    @Column(unique = true, nullable = false )
    private String numRun;

    @NotBlank(message = "El dv no puede estar vacio")
    @Column(length = 1, nullable = false)
    private String dvRun;

    //Primer Nombre
    @NotBlank(message = "No puede estar vacio")
    @Column(length = 50, nullable = false)
    private String primerNombre;

    //Segundo Nombre
    @Column(length = 50)
    private String segundoNombre;
    
    //Primer Apellido
    @NotBlank(message = "Apellido no puede estar vacio")
    @Column(length = 50, nullable = false)
    private String primerApellido;
    
    //Segundo Apellido
    @Column(length = 50)
    private String segundoApellido;

    //Telefono, no integrado en G.Cuentas de Usuario
    @Size(min = 9, max = 12)
    @Column(unique = true, length = 12)
    private String telefono;

    //Correo --@Email pide un formato para ingresar correo valido 
    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "Ingrese un correo válido")
    @Column(unique = true, nullable = false)
    private String correo;

    //Username
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Length(min = 5, max = 50, message = "Username fuera de rango")
    @Column(unique = true, nullable = false)
    private String username;

    //Contraseña
    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 8, max = 30)
    private String password;


    //Estado
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoUsuarioEnum estado;

    //Fecha Nacimiento
    @NotNull(message = "La fecha de nacimiento no puede estar vacia")
    @Past(message = "Ingrese fecha valida")
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    //Fecha registro
    private Instant fechaRegistro;

    //ROL
    //Hace una relacion muchos a muchos 
    //No integra cascada por tema de seguridad (es mejor manejar roles predefinidos y controlados)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles = new ArrayList<>();




    //Método para crear cuenta, se asigna la fecha del sistema en fecha de registro y por defecto se asigna el estado ACTIVO a lla cuenta
    public void crearCuenta(){
        this.fechaRegistro = Instant.now();
        this.estado = EstadoUsuarioEnum.ACTIVO;
    }


}
