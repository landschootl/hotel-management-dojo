package exceptions;

/**
 * This exception is thrown when a functional error occurs in the application.
 */
public class ParamNotValidException extends Exception {

    public ParamNotValidException() {
        super("Param not valid");
    }
}
