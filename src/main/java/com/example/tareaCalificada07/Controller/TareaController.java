package com.example.tareaCalificada07.Controller;

import com.example.tareaCalificada07.Model.Tarea;
import com.example.tareaCalificada07.Service.TareaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public String listarTareas(Model model, Principal principal) {
        model.addAttribute("tareas", tareaService.listarPorUsuario(principal.getName()));
        return "tareas/index";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("tarea", new Tarea());
        return "tareas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model, Principal principal, RedirectAttributes flash) {
        // Llamada actualizada al servicio por id y usuario
        return tareaService.buscarPorIdYUsuario(id, principal.getName())
                .map(tarea -> {
                    model.addAttribute("tarea", tarea);
                    return "tareas/formulario";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("error", "La tarea no existe o no tienes permiso para editarla.");
                    return "redirect:/tareas";
                });
    }

    @PostMapping("/guardar")
    public String guardarTarea(@ModelAttribute("tarea") Tarea tarea, Principal principal, RedirectAttributes flash) {
        tareaService.guardar(tarea, principal.getName());
        flash.addFlashAttribute("success", "Tarea guardada correctamente.");
        return "redirect:/tareas";
    }

    @PostMapping("/completar/{id}")
    public String alternarEstado(@PathVariable("id") Long id, Principal principal, RedirectAttributes flash) {
        tareaService.cambiarEstadoCompletada(id, principal.getName());
        flash.addFlashAttribute("success", "Estado de la tarea actualizado.");
        return "redirect:/tareas";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable("id") Long id, Principal principal, RedirectAttributes flash) {
        tareaService.eliminar(id, principal.getName());
        flash.addFlashAttribute("success", "Tarea eliminada exitosamente.");
        return "redirect:/tareas";
    }
}