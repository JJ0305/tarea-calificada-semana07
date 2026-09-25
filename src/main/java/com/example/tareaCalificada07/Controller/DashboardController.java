package com.example.tareaCalificada07.Controller;

import com.example.tareaCalificada07.Model.Tarea;
import com.example.tareaCalificada07.Service.TareaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
        
        String rol = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_ESTUDIANTE");

        model.addAttribute("correo", correo);
        model.addAttribute("rol", rol);

        List<Tarea> misTareas = tareaService.listarPorUsuario(correo);

        int totalTareas = misTareas.size();
        long tareasPendientes = misTareas.stream().filter(t -> t.getEstado() == 0).count();
        long tareasCompletadas = misTareas.stream().filter(t -> t.getEstado() == 1).count();

        model.addAttribute("totalTareas", totalTareas);
        model.addAttribute("tareasPendientes", tareasPendientes);
        model.addAttribute("tareasCompletadas", tareasCompletadas);

        return "home";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "403";
    }
}
