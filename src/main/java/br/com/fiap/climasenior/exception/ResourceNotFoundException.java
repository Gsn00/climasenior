package br.com.fiap.climasenior.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado(a) com o ID: %d", resourceName, id));
    }
}