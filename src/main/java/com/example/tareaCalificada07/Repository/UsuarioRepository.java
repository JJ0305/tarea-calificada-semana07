package com.example.tareaCalificada07.Repository;

import com.example.tareaCalificada07.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByDni(String dni);

    boolean existsByCorreoIgnoreCase(String correo);

    List<Usuario> findAllByOrderByIdAsc();

    Optional<Usuario> findByCorreo(String correo);
}