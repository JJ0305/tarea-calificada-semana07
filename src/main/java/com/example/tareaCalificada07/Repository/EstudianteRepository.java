package com.example.tareaCalificada07.Repository;

import com.example.tareaCalificada07.Model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    boolean existsByDni(String dni);

    boolean existsByCorreoIgnoreCase(String correo);

    List<Estudiante> findAllByOrderByIdAsc();

    Optional<Estudiante> findByCorreo(String correo);
}
