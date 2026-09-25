package com.example.tareaCalificada07.Model;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usuario_id")
    private Long id;

    @NotBlank(message = "Ingresa los nombres del usuario")
    @Size(max = 60, message = "Los nombres pueden tener hasta 60 caracteres")
    @Column(nullable = false, length = 60)
    private String nombres;

    @NotBlank(message = "Ingresa los apellidos del usuario")
    @Size(max = 60, message = "Los apellidos pueden tener hasta 60 caracteres")
    @Column(nullable = false, length = 60)
    private String apellidos;

    @NotBlank(message = "Ingresa el DNI")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos")
    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @NotBlank(message = "Ingresa el correo")
    @Email(message = "Ingresa un correo válido, por ejemplo nombre@correo.com")
    @Size(max = 100, message = "El correo puede tener hasta 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Pattern(regexp = "9\\d{8}", message = "El celular debe tener 9 dígitos y empezar con 9")
    @Column(length = 9)
    private String telefono;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas = new ArrayList<>();

    @NotBlank(message = "Ingresa la contraseña")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String rol = "ESTUDIANTE";

    @Column(nullable = false)
    private boolean activo = true;

    public Usuario() {
    }

    public Usuario(String nombres, String apellidos, String dni, String correo, String telefono, String password) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
        this.rol = "ESTUDIANTE";
        this.activo = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public List<Tarea> getTareas() { return tareas;}

    public void setTareas(List<Tarea> tareas) {this.tareas = tareas;}
    
}

    

