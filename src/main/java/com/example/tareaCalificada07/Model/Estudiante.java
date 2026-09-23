package com.example.tareaCalificada07.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Estudiante {

    private Long id;

    @NotBlank(message = "Ingresa los nombres del estudiante")
    @Size(max = 60, message = "Los nombres pueden tener hasta 60 caracteres")
    private String nombres;

    @NotBlank(message = "Ingresa los apellidos del estudiante")
    @Size(max = 60, message = "Los apellidos pueden tener hasta 60 caracteres")
    private String apellidos;

    @NotBlank(message = "Ingresa el DNI")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos")
    private String dni;

    @NotBlank(message = "Ingresa el correo")
    @Email(message = "Ingresa un correo válido, por ejemplo nombre@correo.com")
    private String correo;
    
    @Pattern(regexp = "9\\d{8}", message = "El celular debe tener 9 dígitos y empezar con 9")
    private String telefono;

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
}
