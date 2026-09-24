package com.example.tareaCalificada07.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class LoginController {

    /** Página de login */
    @GetMapping("/login")
    public String mostrarLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMsg", "Correo o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "Sesión cerrada correctamente");
        }
        return "login";
    }

    /** Página de bienvenida (requiere login) */
    @GetMapping({"/", "/home"})
    public String mostrarHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("correo", auth.getName());
        model.addAttribute("rol", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER"));

        return "home";
    }
}