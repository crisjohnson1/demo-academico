package co.edu.demoacademico.service;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException(String email) {
        super("Ya existe un estudiante con el email: " + email);
    }
}
