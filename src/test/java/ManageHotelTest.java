import entities.Booking;
import entities.Hotel;
import entities.Room;
import exceptions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManageHotelTest {

    private String NEW_CUSTOMER_FULLNAME;
    private Room ROOM;
    private Hotel HOTEL;
    private Booking BOOKING;
    private ManageHotel MANAGE_HOTEL;

    @BeforeEach
    public void setUp() {
        NEW_CUSTOMER_FULLNAME = "Dupont Bernard";
        ROOM = Room.builder().roomNumber(1).capacity(2).price(70).build();
        BOOKING = Booking.builder().reference(0).roomNumber(1).fullName("John Doe").checkInDate(LocalDate.now().plusMonths(2)).checkOutDate(LocalDate.now().plusMonths(2).plusDays(3)).build();
        HOTEL = Hotel.builder()
                .rooms(List.of(ROOM))
                .bookings(new ArrayList<>(Collections.singletonList(BOOKING))).build();
        MANAGE_HOTEL = ManageHotel.builder()
                .hotel(HOTEL)
                .build();
    }


    /**
     * Test for searchBooking method.
     */

    @Test
    public void should_throw_exception_when_search_with_not_valid_param() {
        // the cas if full name is null
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.searchBooking(BOOKING.getRoomNumber(), null, BOOKING.getCheckInDate()));
        // the cas if full name is empty
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.searchBooking(BOOKING.getRoomNumber(), "", BOOKING.getCheckInDate()));
        // the cas if date is null
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.searchBooking(BOOKING.getRoomNumber(), BOOKING.getFullName(), null));
    }

    @Test
    public void should_throw_exception_when_search_with_not_exist_room() {
        // the cas if room not exist
        assertThrows(RoomNotFoundException.class, () -> MANAGE_HOTEL.searchBooking(2, BOOKING.getFullName(), BOOKING.getCheckInDate()));
    }

    @Test
    public void should_throw_exception_when_search_with_not_found_booking() {
        // the cas if booking with full name not exist
        assertThrows(BookingNotFoundException.class, () -> MANAGE_HOTEL.searchBooking(BOOKING.getRoomNumber(), "not exist", BOOKING.getCheckInDate()));
        // the cas if booking with date not exist
        assertThrows(BookingNotFoundException.class, () -> MANAGE_HOTEL.searchBooking(BOOKING.getRoomNumber(), BOOKING.getFullName(), LocalDate.now()));
    }

    @Test
    public void should_return_booking_when_search() throws BookingNotFoundException, RoomNotFoundException, ParamNotValidException {
        // the cas if BOOKING with date equals checkInDate
        Booking result = MANAGE_HOTEL.searchBooking(BOOKING.getRoomNumber(), BOOKING.getFullName(), BOOKING.getCheckInDate());
        assertEquals(BOOKING, result);
        // the cas if BOOKING with date equals checkOutDate
        result = MANAGE_HOTEL.searchBooking(BOOKING.getRoomNumber(), BOOKING.getFullName(), BOOKING.getCheckOutDate());
        assertEquals(BOOKING, result);
        // the cas if BOOKING with date between checkInDate and checkOutDate
        result = MANAGE_HOTEL.searchBooking(BOOKING.getRoomNumber(), BOOKING.getFullName(), BOOKING.getCheckInDate().plusDays(1));
        assertEquals(BOOKING, result);
    }

    /**
     * Test for bookRoom method.
     */

    @Test
    public void should_throw_exception_when_book_with_not_exist_room() {
        // the cas if room not exist
        assertThrows(RoomNotFoundException.class, () -> MANAGE_HOTEL.bookRoom(2, LocalDate.now(), LocalDate.now().plusDays(1), NEW_CUSTOMER_FULLNAME));
    }

    @Test
    public void should_throw_exception_when_book_with_not_valid_param() {
        // the cas if checkInDate is null
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), null, LocalDate.now(), NEW_CUSTOMER_FULLNAME));
        // the cas if checkOutDate is null
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), LocalDate.now(), null, NEW_CUSTOMER_FULLNAME));
        // the cas if full name is null
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), LocalDate.now(), LocalDate.now().plusDays(1), null));
        // the cas if full name is empty
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), LocalDate.now(), LocalDate.now().plusDays(1), ""));
        // the cas if checkInDate is after checkOutDate
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), LocalDate.now().plusDays(1), LocalDate.now(), NEW_CUSTOMER_FULLNAME));
        // the cas if checkInDate is before now
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), NEW_CUSTOMER_FULLNAME));
        // the cas if checkOutDate is before now
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), LocalDate.now(), LocalDate.now().minusDays(1), NEW_CUSTOMER_FULLNAME));
        // the cas if checkInDate is equal checkOutDate
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), LocalDate.now(), LocalDate.now(), NEW_CUSTOMER_FULLNAME));
    }

    @Test
    public void should_throw_exception_when_book_with_not_available_room() {
        // the cas if checkIn is before checkInDate and checkOut is after checkInDate
        assertThrows(RoomNotAvailableException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate().minusDays(1), BOOKING.getCheckInDate().plusDays(1), NEW_CUSTOMER_FULLNAME));
        // the cas if checkIn is before checkOutDate and checkOut is after checkOutDate
        assertThrows(RoomNotAvailableException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckOutDate().minusDays(1), BOOKING.getCheckOutDate().plusDays(1), NEW_CUSTOMER_FULLNAME));
        // the cas if checkIn is before checkInDate and checkOut is after checkOutDate
        assertThrows(RoomNotAvailableException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate().minusDays(1), BOOKING.getCheckOutDate().plusDays(1), NEW_CUSTOMER_FULLNAME));
        // the cas if checkIn is after checkInDate and checkOut is before checkOutDate
        assertThrows(RoomNotAvailableException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate().plusDays(1), BOOKING.getCheckOutDate().minusDays(1), NEW_CUSTOMER_FULLNAME));
        // the cas if checkIn is equal checkInDate and checkOut is equal checkOutDate
        assertThrows(RoomNotAvailableException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate(), BOOKING.getCheckOutDate(), NEW_CUSTOMER_FULLNAME));
        // the cas if checkIn is equal checkInDate and checkOut is after checkOutDate
        assertThrows(RoomNotAvailableException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate(), BOOKING.getCheckOutDate().plusDays(1), NEW_CUSTOMER_FULLNAME));
        // the cas if checkIn is equal checkInDate and checkOut is before checkOutDate
        assertThrows(RoomNotAvailableException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate(), BOOKING.getCheckOutDate().minusDays(1), NEW_CUSTOMER_FULLNAME));
        // the cas if checkIn is before checkInDate and checkOut is equal checkOutDate
        assertThrows(RoomNotAvailableException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate().minusDays(1), BOOKING.getCheckOutDate(), NEW_CUSTOMER_FULLNAME));
        // the cas if checkIn is after checkInDate and checkOut is equal checkOutDate
        assertThrows(RoomNotAvailableException.class, () -> MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate().plusDays(1), BOOKING.getCheckOutDate(), NEW_CUSTOMER_FULLNAME));
    }

    @Test
    public void should_book_room_when_book() throws RoomNotFoundException, RoomNotAvailableException, BookingNotValidException, ParamNotValidException {
        // the cas if checkIn is before checkInDate and checkOut is before checkInDate
        MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate().minusDays(2), BOOKING.getCheckInDate().minusDays(1), NEW_CUSTOMER_FULLNAME);
        assertEquals(2, HOTEL.getBookings().size());
        // the cas if checkIn is before checkInDate and checkOut is equal checkInDate
        HOTEL.setBookings(new ArrayList<>(Collections.singletonList(BOOKING)));
        MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckInDate().minusDays(2), BOOKING.getCheckInDate(), NEW_CUSTOMER_FULLNAME);
        assertEquals(2, HOTEL.getBookings().size());
        // the cas if checkIn is equal checkOutDate and checkOut is after checkOutDate
        HOTEL.setBookings(new ArrayList<>(Collections.singletonList(BOOKING)));
        MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckOutDate(), BOOKING.getCheckOutDate().plusDays(2), NEW_CUSTOMER_FULLNAME);
        assertEquals(2, HOTEL.getBookings().size());
        // the cas if checkIn is after checkOutDate and checkOut is after checkOutDate
        HOTEL.setBookings(new ArrayList<>(Collections.singletonList(BOOKING)));
        MANAGE_HOTEL.bookRoom(ROOM.getRoomNumber(), BOOKING.getCheckOutDate().plusDays(1), BOOKING.getCheckOutDate().plusDays(2), NEW_CUSTOMER_FULLNAME);
        assertEquals(2, HOTEL.getBookings().size());
    }

    /**
     * Test for cancelBooking method.
     */

    @Test
    public void throw_exception_when_cancel_with_not_found_booking() {
        // the cas if booking not exist
        assertThrows(BookingNotFoundException.class, () -> MANAGE_HOTEL.cancelBooking(2));
    }

    public void should_cancel_room_when_cancel() throws BookingNotFoundException {
        MANAGE_HOTEL.cancelBooking(0);
        assertEquals(0, HOTEL.getBookings().size());
    }

    /**
     * Test for suggestRoom method.
     */

    @Test
    public void should_throw_exception_when_suggest_with_not_valid_param() {
        int capacity = 2;
        // the cas if checkInDate is null
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.suggestRoom(capacity, null, LocalDate.now()));
        // the cas if checkOutDate is null
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.suggestRoom(capacity, LocalDate.now(), null));
        // the cas if checkInDate is after checkOutDate
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.suggestRoom(capacity, LocalDate.now().plusDays(1), LocalDate.now()));
        // the cas if checkInDate is before now
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.suggestRoom(capacity, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)));
        // the cas if checkOutDate is before now
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.suggestRoom(capacity, LocalDate.now(), LocalDate.now().minusDays(1)));
        // the cas if checkInDate is equal checkOutDate
        assertThrows(ParamNotValidException.class, () -> MANAGE_HOTEL.suggestRoom(capacity, LocalDate.now(), LocalDate.now()));
    }

    @Test
    public void should_return_empty_list_when_suggest_with_not_available_room() throws ParamNotValidException {
        // the cas if not exist room for capacity
        List<Room> result = MANAGE_HOTEL.suggestRoom(10, LocalDate.now(), LocalDate.now().plusDays(2));
        assertEquals(0, result.size());
        // the cas if exist room for capacity but not available for date
        result = MANAGE_HOTEL.suggestRoom(ROOM.getCapacity(), BOOKING.getCheckInDate(), BOOKING.getCheckOutDate());
        assertEquals(0, result.size());
    }

    @Test
    public void should_return_list_room_when_suggest() throws ParamNotValidException {
        List<Room> result = MANAGE_HOTEL.suggestRoom(ROOM.getCapacity(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        assertEquals(1, result.size());
        assertEquals(ROOM, result.get(0));
    }
}