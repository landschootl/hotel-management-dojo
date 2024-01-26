package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hotel with rooms and bookings.
 */
@Getter
@Setter
@Builder
@ToString
public class Hotel {

    @Builder.Default
    private List<Room> rooms = new ArrayList<>();
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();

    /**
     * Checks if a room with the given room number exists in the hotel.
     *
     * @param roomNumber the room number to check
     * @return true if the room exists, false otherwise
     */
    public boolean checkRoomExists(int roomNumber) {
        return rooms.stream()
                .anyMatch(r -> r.getRoomNumber() == roomNumber);
    }

    /**
     * Retrieves all bookings for a specific room.
     *
     * @param roomNumber the room number to get bookings for
     * @return a list of bookings for the room
     */
    public List<Booking> getBookingsForRoom(int roomNumber) {
        return bookings.stream()
                .filter(b -> b.getRoomNumber() == roomNumber)
                .toList();
    }

    /**
     * Adds a booking to the hotel.
     *
     * @param booking the booking to add
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Removes a booking from the hotel.
     *
     * @param booking the booking to remove
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Checks if a room is available for booking within the specified dates.
     *
     * @param roomNumber   the room number to check availability for
     * @param checkInDate  the check-in date
     * @param checkOutDate the check-out date
     * @return true if the room is available, false otherwise
     */
    public boolean checkRoomAvailability(int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        //get all bookings for this room
        List<Booking> bookingsForRoom = getBookingsForRoom(roomNumber);

        //check if room is available
        return bookingsForRoom.stream()
                .filter(booking -> ((checkInDate.isBefore(booking.getCheckInDate()) || checkInDate.isEqual(booking.getCheckInDate())) && checkOutDate.isAfter(booking.getCheckInDate()))
                        || ((checkInDate.isBefore(booking.getCheckOutDate())) && (checkOutDate.isAfter(booking.getCheckOutDate()) || checkOutDate.isEqual(booking.getCheckOutDate())))
                        || ((checkInDate.isAfter(booking.getCheckInDate()) || checkInDate.isEqual(booking.getCheckInDate())) && (checkOutDate.isBefore(booking.getCheckOutDate()) || checkOutDate.isEqual(booking.getCheckOutDate()))))
                .findFirst().isEmpty();
    }
}
