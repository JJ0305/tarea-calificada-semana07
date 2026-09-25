package com.example.tareaCalificada07.Service;

import com.example.tareaCalificada07.Model.Usuario;
import com.example.tareaCalificada07.Model.Tarea;
import com.example.tareaCalificada07.Repository.UsuarioRepository;
import com.example.tareaCalificada07.Repository.TareaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    public TareaService(TareaRepository tareaRepository, UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario obtenerUsuarioAutenticado(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));
    }

    @Transactional(readOnly = true)
    public List<Tarea> listarPorUsuario(String correo) {
        Usuario usuario = obtenerUsuarioAutenticado(correo);
        return tareaRepository.findByUsuarioAndEstadoNot(usuario, 2);
    }

    @Transactional(readOnly = true)
    public Optional<Tarea> buscarPorIdYUsuario(Long id, String correo) {
        Usuario usuario = obtenerUsuarioAutenticado(correo);
        return tareaRepository.findByIdAndUsuario(id, usuario);
    }

    @Transactional
    public Tarea guardar(Tarea tarea, String correo) {
        Usuario usuario = obtenerUsuarioAutenticado(correo);
        tarea.setUsuario(usuario);
        if (tarea.getEstado() == null) {
            tarea.setEstado(0);
        }
        return tareaRepository.save(tarea);
    }

    @Transactional
    public void eliminar(Long id, String correo) {
        Usuario usuario = obtenerUsuarioAutenticado(correo);
        tareaRepository.findByIdAndUsuario(id, usuario).ifPresent(tarea -> {
            tarea.setEstado(2); 
            tareaRepository.save(tarea);
        });
    }

    @Transactional
    public void cambiarEstadoCompletada(Long id, String correo) {
        Usuario usuario = obtenerUsuarioAutenticado(correo);
        tareaRepository.findByIdAndUsuario(id, usuario).ifPresent(tarea -> {
            if (tarea.getEstado() == 0) {
                tarea.setEstado(1);
            } else if (tarea.getEstado() == 1) {
                tarea.setEstado(0);
            }
            tareaRepository.save(tarea);
        });
    }
}