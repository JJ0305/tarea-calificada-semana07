package com.example.tareaCalificada07.Service;

import com.example.tareaCalificada07.Exception.UsuarioDuplicadoException;
import com.example.tareaCalificada07.Model.Usuario;
import com.example.tareaCalificada07.Repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public Usuario registrar(Usuario usuario) {
        usuario.setNombres(usuario.getNombres().trim());
        usuario.setApellidos(usuario.getApellidos().trim());
        usuario.setDni(usuario.getDni().trim());
        usuario.setCorreo(usuario.getCorreo().trim().toLowerCase(Locale.ROOT));

        if (repository.existsByDni(usuario.getDni())) {
            throw new UsuarioDuplicadoException("dni", "Ya existe un usuario registrado con ese DNI");
        }
        if (repository.existsByCorreoIgnoreCase(usuario.getCorreo())) {
            throw new UsuarioDuplicadoException("correo", "Ya existe un usuario registrado con ese correo");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        if (usuario.getRol() == null || usuario.getRol().isBlank()) {
            usuario.setRol("ROLE_USER");
        }

        return repository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return repository.findAllByOrderByIdAsc();
    }
}