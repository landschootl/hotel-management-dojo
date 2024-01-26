package exceptions;

/**
 * This exception is thrown when a functional error occurs in the application.
 */
public class BookingNotValidException extends Exception {

    public BookingNotValidException() {
        super("Booking not valid");
    }
}
