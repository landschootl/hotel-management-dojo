package exceptions;

/**
 * This exception is thrown when a functional error occurs in the application.
 */
public class RoomNotFoundException extends Exception {

    public RoomNotFoundException() {
        super("Room not found");
    }
}
