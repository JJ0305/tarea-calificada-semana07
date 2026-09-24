package com.example.tareaCalificada07.repository;

import com.example.tareaCalificada07.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    boolean existsByDni(String dni);

    boolean existsByCorreoIgnoreCase(String correo);

    List<Estudiante> findAllByOrderByIdAsc();
}
