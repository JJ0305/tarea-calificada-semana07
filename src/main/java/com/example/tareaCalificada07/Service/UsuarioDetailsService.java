package com.example.tareaCalificada07.Service;

import com.example.tareaCalificada07.Model.Usuario;
import com.example.tareaCalificada07.Repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe un usuario con el correo: " + correo));

        return User.builder()
                .username(user.getCorreo())
                .password(user.getPassword())
                .disabled(!user.isActivo())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRol())))
                .build();
    }
}