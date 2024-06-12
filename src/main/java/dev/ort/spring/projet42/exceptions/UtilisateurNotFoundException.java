package dev.ort.spring.projet42.exceptions;

public class UtilisateurNotFoundException extends Exception{

    public UtilisateurNotFoundException(String message) {
        super(message);
    }

    public UtilisateurNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
