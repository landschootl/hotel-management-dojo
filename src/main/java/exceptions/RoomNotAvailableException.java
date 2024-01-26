package exceptions;

/**
 * This exception is thrown when a functional error occurs in the application.
 */
public class RoomNotAvailableException extends Exception {

    public RoomNotAvailableException() {
        super("Room not available");
    }
}
