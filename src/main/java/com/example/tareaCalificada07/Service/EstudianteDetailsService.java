package com.example.tareaCalificada07.Service;

import com.example.tareaCalificada07.Model.Estudiante;
import com.example.tareaCalificada07.Repository.EstudianteRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteDetailsService implements UserDetailsService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteDetailsService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Estudiante est = estudianteRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No existe un estudiante con el correo: " + correo));

        return User.builder()
                .username(est.getCorreo())
                .password(est.getPassword())
                .disabled(!est.isActivo())
                .authorities(List.of(new SimpleGrantedAuthority(est.getRol())))
                .build();
    }
}