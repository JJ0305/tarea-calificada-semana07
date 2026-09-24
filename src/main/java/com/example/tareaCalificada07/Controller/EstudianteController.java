package com.example.tareaCalificada07.controller;

import com.example.tareaCalificada07.exception.EstudianteDuplicadoException;
import com.example.tareaCalificada07.model.Estudiante;
import com.example.tareaCalificada07.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @InitBinder
    public void configurarBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listar());
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "estudiantes/formulario";
    }

    @PostMapping
    public String registrar(@Valid @ModelAttribute("estudiante") Estudiante estudiante,
                            BindingResult resultado,
                            RedirectAttributes redirect) {
        if (resultado.hasErrors()) {
            return "estudiantes/formulario";
        }
        try {
            Estudiante guardado = estudianteService.registrar(estudiante);
            redirect.addFlashAttribute("mensaje",
                    "Se registró a " + guardado.getNombres() + " " + guardado.getApellidos());
            return "redirect:/estudiantes";
        } catch (EstudianteDuplicadoException ex) {
            resultado.rejectValue(ex.getCampo(), "duplicado", ex.getMessage());
            return "estudiantes/formulario";
        }
    }
}