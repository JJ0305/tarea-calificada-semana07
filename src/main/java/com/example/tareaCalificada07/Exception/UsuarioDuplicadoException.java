package com.example.tareaCalificada07.Exception;

public class UsuarioDuplicadoException extends RuntimeException {

    private final String campo;

    public UsuarioDuplicadoException(String campo, String mensaje) {
        super(mensaje);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}