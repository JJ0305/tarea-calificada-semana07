package com.example.tareaCalificada07.exception;

public class EstudianteDuplicadoException extends RuntimeException {

    private final String campo;

    public EstudianteDuplicadoException(String campo, String mensaje) {
        super(mensaje);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}