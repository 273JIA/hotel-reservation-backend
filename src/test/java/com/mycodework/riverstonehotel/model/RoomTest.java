package com.mycodework.riverstonehotel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    private Room room;
    private BookedRoom bookedRoom;

    @BeforeEach
    public void setUp() throws SQLException {
        room = new Room();
        room.setRoomType("Single");
        room.setRoomPrice(BigDecimal.valueOf(100));
        room.setPhoto(null); // Set a mock Blob if needed

        bookedRoom = new BookedRoom();
        bookedRoom.setRoom(room);
    }

    @Test
    public void testRoomConstructorAndGetters() {
        assertEquals("Single", room.getRoomType());
        assertEquals(BigDecimal.valueOf(100), room.getRoomPrice());
        assertFalse(room.isBooked());
        assertNotNull(room.getBookings());
        assertEquals(0, room.getBookings().size());
    }

    @Test
    public void testAddBooking() {
        room.addBooking(bookedRoom);

        assertEquals(1, room.getBookings().size());
        assertTrue(room.isBooked());
        assertEquals(room, bookedRoom.getRoom());
        assertNotNull(bookedRoom.getBookingConfirmationCode());
        assertEquals(10, bookedRoom.getBookingConfirmationCode().length());
        assertTrue(bookedRoom.getBookingConfirmationCode().chars().allMatch(Character::isDigit));
    }

    @Test
    public void testPhoto() throws SQLException {
        Blob blob = new javax.sql.rowset.serial.SerialBlob("photo".getBytes());
        room.setPhoto(blob);
        assertEquals(blob, room.getPhoto());
    }
}
