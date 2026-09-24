package com.example.tareaCalificada07.service;

import com.example.tareaCalificada07.exception.EstudianteDuplicadoException;
import com.example.tareaCalificada07.model.Estudiante;
import com.example.tareaCalificada07.repository.EstudianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
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
        return repository.save(estudiante);
    }

    @Transactional(readOnly = true)
    public List<Estudiante> listar() {
        return repository.findAllByOrderByIdAsc();
    }
}
