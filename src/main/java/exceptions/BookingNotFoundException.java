package exceptions;

/**
 * This exception is thrown when a functional error occurs in the application.
 */
public class BookingNotFoundException extends Exception {

    public BookingNotFoundException() {
        super("Booking not found");
    }
}
