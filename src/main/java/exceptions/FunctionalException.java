package exceptions;

/**
 * This exception is thrown when a functional error occurs in the application.
 */
public class FunctionalException extends Exception {

    public FunctionalException(String message) {
        super(message);
    }
}
