package com.example.tareaCalificada07.Controller;

import com.example.tareaCalificada07.Service.TareaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final TareaService tareaService;

    public DashboardController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping({"/", "/home", "/dashboard"})
    public String mostrarDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        
        model.addAttribute("correo", correo);
        model.addAttribute("rol", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER"));

        int totalTareas = tareaService.listarPorUsuario(correo).size();
        model.addAttribute("totalTareas", totalTareas);

        return "home";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "403";
    }
}