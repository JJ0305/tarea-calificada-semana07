package com.example.tareaCalificada07.Repository;

import com.example.tareaCalificada07.Model.Usuario;
import com.example.tareaCalificada07.Model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    List<Tarea> findByUsuarioAndEstadoNot(Usuario usuario, Integer estado);

    Optional<Tarea> findByIdAndUsuario(Long id, Usuario usuario);
}