package com.mycodework.riverstonehotel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class BookedRoomTest {

    private BookedRoom bookedRoom;
    private Room room;

    @BeforeEach
    public void setUp() {
        bookedRoom = new BookedRoom();
        bookedRoom.setCheckInDate(LocalDate.of(2024, 7, 15));
        bookedRoom.setCheckOutDate(LocalDate.of(2024, 7, 20));
        bookedRoom.setNumOfAdults(2);
        bookedRoom.setNumOfChildren(1);

        room = new Room();
        room.setId(1L);
        bookedRoom.setRoom(room);
    }

    @Test
    public void testCalculateTotalNumOfGuest() {
        bookedRoom.calculateTotalNumOfGuest();
        assertEquals(3, bookedRoom.getTotalNumOfGuest());
    }

    @Test
    public void testSetNumOfAdults() {
        bookedRoom.setNumOfAdults(3);
        assertEquals(3, bookedRoom.getNumOfAdults());
        assertEquals(4, bookedRoom.getTotalNumOfGuest()); // Adults + Children
    }

    @Test
    public void testSetNumOfChildren() {
        bookedRoom.setNumOfChildren(2);
        assertEquals(2, bookedRoom.getNumOfChildren());
        assertEquals(4, bookedRoom.getTotalNumOfGuest()); // Adults + Children
    }

    @Test
    public void testSetBookingConfirmationCode() {
        bookedRoom.setBookingConfirmationCode("ABC123");
        assertEquals("ABC123", bookedRoom.getBookingConfirmationCode());
    }

    @Test
    public void testRoomAssociation() {
        assertEquals(room, bookedRoom.getRoom());
    }
}
