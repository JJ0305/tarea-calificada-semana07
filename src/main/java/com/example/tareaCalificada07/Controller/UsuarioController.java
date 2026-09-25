package com.example.tareaCalificada07.Controller;

import com.example.tareaCalificada07.Exception.UsuarioDuplicadoException;
import com.example.tareaCalificada07.Model.Usuario;
import com.example.tareaCalificada07.Service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @InitBinder
    public void configurarBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listar());
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
    }

    @PostMapping
    public String registrar(@Valid @ModelAttribute("usuario") Usuario usuario,
                            BindingResult resultado,
                            RedirectAttributes redirect) {
        if (resultado.hasErrors()) {
            return "usuarios/formulario";
        }

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            usuario.setPassword(usuario.getDni());
        }
        try {
            Usuario guardado = usuarioService.registrar(usuario);
            redirect.addFlashAttribute("mensaje",
                    "Se registró a " + guardado.getNombres() + " " + guardado.getApellidos()
                + ". Contraseña inicial: su DNI.");
            return "redirect:/usuarios";
        } catch (UsuarioDuplicadoException ex) {
            resultado.rejectValue(ex.getCampo(), "duplicado", ex.getMessage());
            return "usuarios/formulario";
        }
    }
}