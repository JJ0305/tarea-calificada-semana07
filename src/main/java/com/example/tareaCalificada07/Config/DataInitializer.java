package com.example.tareaCalificada07.Config;

import com.example.tareaCalificada07.Model.Usuario;
import com.example.tareaCalificada07.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepo,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepo.count() == 0) {

                Usuario admin = new Usuario();
                admin.setNombres("Admin");
                admin.setApellidos("Sistema");
                admin.setDni("12345678");
                admin.setCorreo("admin@uni.edu");
                admin.setTelefono("987654321");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRol("DOCENTE");
                admin.setActivo(true);
                usuarioRepo.save(admin);

                Usuario user = new Usuario();
                user.setNombres("Juan");
                user.setApellidos("Pérez");
                user.setDni("87654321");
                user.setCorreo("juan@uni.edu");
                user.setTelefono("912345678");
                user.setPassword(passwordEncoder.encode("juan123"));
                user.setRol("ESTUDIANTE");
                user.setActivo(true);
                usuarioRepo.save(user);

                System.out.println("Usuarios de prueba creados:");
                System.out.println("   admin@uni.edu / admin123 (ADMIN)");
                System.out.println("   juan@uni.edu / juan123   (USER)");
            }
        };
    }
}