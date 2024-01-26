import entities.Booking;
import entities.Hotel;
import entities.Room;
import exceptions.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a class for managing a hotel.
 */
@Getter
@Builder
public class ManageHotel {

    private Hotel hotel;

    /**
     * Searches for a booking based on the room number, guest's full name, and date.
     *
     * @param roomNumber the room number to search for
     * @param fullName the full name of the guest to search for
     * @param date the date to search for
     * @return the booking that matches the search criteria
     * @throws RoomNotFoundException if the room does not exist
     * @throws BookingNotFoundException if the booking is not found
     */
    public Booking searchBooking(int roomNumber, String fullName, LocalDate date) throws RoomNotFoundException, BookingNotFoundException, ParamNotValidException {
        //check if param is valid
        if (date == null || fullName == null || fullName.isEmpty()) {
            throw new ParamNotValidException();
        }

        //check if room exists
        if (!hotel.checkRoomExists(roomNumber)) {
            throw new RoomNotFoundException();
        }

        //get all bookings for this room
        List<Booking> bookingsForRoom = hotel.getBookingsForRoom(roomNumber);

        //search booking
        return bookingsForRoom.stream()
                .filter(booking -> booking.getFullName().equals(fullName))
                .filter(booking -> (date.isAfter(booking.getCheckInDate()) && date.isBefore(booking.getCheckOutDate())) || date.isEqual(booking.getCheckInDate()) || date.isEqual(booking.getCheckOutDate()))
                .findFirst()
                .orElseThrow(BookingNotFoundException::new);
    }

    /**
     * Books a room with the given details.
     *
     * @param roomNumber   the room number to book
     * @param checkInDate  the check-in date
     * @param checkOutDate the check-out date
     * @param fullName     the full name of the guest
     * @throws RoomNotAvailableException if the room is not available for the given dates
     * @throws RoomNotFoundException     if the room does not exist
     * @throws BookingNotValidException  if the booking is not valid
     */
    public void bookRoom(int roomNumber, LocalDate checkInDate, LocalDate checkOutDate, String fullName) throws RoomNotAvailableException, RoomNotFoundException, BookingNotValidException, ParamNotValidException {
        //check if param is valid
        if (checkInDate == null || checkOutDate == null || fullName == null || fullName.isEmpty() || checkInDate.isAfter(checkOutDate) || checkInDate.isBefore(LocalDate.now()) || checkOutDate.isBefore(LocalDate.now()) || checkInDate.isEqual(checkOutDate)) {
            throw new ParamNotValidException();
        }

        //check if room exists
        if (!hotel.checkRoomExists(roomNumber)) {
            throw new RoomNotFoundException();
        }

        //check if room is available
        if (!hotel.checkRoomAvailability(roomNumber, checkInDate, checkOutDate)) {
            throw new RoomNotAvailableException();
        }

        //create booking
        Booking booking = Booking.builder()
                .reference(Booking.nextReferenceCount())
                .roomNumber(roomNumber)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .fullName(fullName)
                .build();

        //book room
        hotel.addBooking(booking);
    }

    /**
     * Cancels a booking based on the provided reference.
     *
     * @param reference the reference of the booking to be canceled
     */
    public void cancelBooking(int reference) throws BookingNotFoundException {
        //search booking
        Booking booking = hotel.getBookings().stream()
                .filter(b -> reference == b.getReference())
                .findFirst()
                .orElseThrow(BookingNotFoundException::new);

        //cancel booking
        hotel.removeBooking(booking);
    }

    /**
     * Returns a list of rooms that can accommodate the given capacity and are available between the specified check-in and check-out dates.
     *
     * @param capacity     the desired capacity of the room
     * @param checkInDate  the check-in date
     * @param checkOutDate the check-out date
     * @return a list of rooms that meet the criteria
     */
    public List<Room> suggestRoom(int capacity, LocalDate checkInDate, LocalDate checkOutDate) throws ParamNotValidException {
        //check if param is valid
        if (checkInDate == null || checkOutDate == null || capacity <= 0 || checkInDate.isAfter(checkOutDate) || checkInDate.isBefore(LocalDate.now()) || checkOutDate.isBefore(LocalDate.now()) || checkInDate.isEqual(checkOutDate)) {
            throw new ParamNotValidException();
        }

        //get all rooms with capacity
        List<Room> roomsWithCapacity = hotel.getRooms().stream()
                .filter(r -> r.getCapacity() == capacity)
                .toList();

        //filter rooms with availability
        return roomsWithCapacity.stream()
                .filter(r -> hotel.checkRoomAvailability(r.getRoomNumber(), checkInDate, checkOutDate))
                .toList();
    }
}
