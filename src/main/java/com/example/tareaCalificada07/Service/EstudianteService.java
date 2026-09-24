package com.example.tareaCalificada07.Service;

import com.example.tareaCalificada07.Exception.EstudianteDuplicadoException;
import com.example.tareaCalificada07.Model.Estudiante;
import com.example.tareaCalificada07.Repository.EstudianteRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Locale;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public EstudianteService(EstudianteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public Estudiante registrar(Estudiante estudiante) {
        estudiante.setNombres(estudiante.getNombres().trim());
        estudiante.setApellidos(estudiante.getApellidos().trim());
        estudiante.setDni(estudiante.getDni().trim());
        estudiante.setCorreo(estudiante.getCorreo().trim().toLowerCase(Locale.ROOT));

        if (repository.existsByDni(estudiante.getDni())) {
            throw new EstudianteDuplicadoException("dni", "Ya existe un estudiante registrado con ese DNI");
        }
        if (repository.existsByCorreoIgnoreCase(estudiante.getCorreo())) {
            throw new EstudianteDuplicadoException("correo", "Ya existe un estudiante registrado con ese correo");
        }

        estudiante.setPassword(passwordEncoder.encode(estudiante.getPassword()));

        if (estudiante.getRol() == null || estudiante.getRol().isBlank()) {
            estudiante.setRol("ROLE_USER");
        }

        return repository.save(estudiante);
    }

    @Transactional(readOnly = true)
    public List<Estudiante> listar() {
        return repository.findAllByOrderByIdAsc();
    }
}
