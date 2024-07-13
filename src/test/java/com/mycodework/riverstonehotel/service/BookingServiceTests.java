package com.mycodework.riverstonehotel.service;

import com.mycodework.riverstonehotel.exception.ResourceNotFoundException;
import com.mycodework.riverstonehotel.model.BookedRoom;
import com.mycodework.riverstonehotel.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookingServiceTests {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private IRoomService roomService;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCancelBooking() {
        // Arrange
        Long bookingId = 1L;

        // Act
        bookingService.cancelBooking(bookingId);

        // Assert
        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

    @Test
    void testGetAllBookings() {
        // Arrange
        List<BookedRoom> expectedBookings = new ArrayList<>();
        when(bookingRepository.findAll()).thenReturn(expectedBookings);

        // Act
        List<BookedRoom> actualBookings = bookingService.getAllBookings();

        // Assert
        assertEquals(expectedBookings, actualBookings);
    }

    @Test
    void testGetBookingsByUserEmail() {
        // Arrange
        String email = "test@example.com";
        List<BookedRoom> expectedBookings = new ArrayList<>();
        when(bookingRepository.findByGuestEmail(email)).thenReturn(expectedBookings);

        // Act
        List<BookedRoom> actualBookings = bookingService.getBookingsByUserEmail(email);

        // Assert
        assertEquals(expectedBookings, actualBookings);
    }

    @Test
    void testGetAllBookingsByRoomId() {
        // Arrange
        Long roomId = 1L;
        List<BookedRoom> expectedBookings = new ArrayList<>();
        when(bookingRepository.findByRoomId(roomId)).thenReturn(expectedBookings);

        // Act
        List<BookedRoom> actualBookings = bookingService.getAllBookingsByRoomId(roomId);

        // Assert
        assertEquals(expectedBookings, actualBookings);
    }

    @Test
    void testFindByBookingConfirmationCode_WhenBookingExists() {
        // Arrange
        String confirmationCode = "ABC123";
        BookedRoom expectedBooking = new BookedRoom();
        when(bookingRepository.findByBookingConfirmationCode(confirmationCode)).thenReturn(Optional.of(expectedBooking));

        // Act
        BookedRoom actualBooking = bookingService.findByBookingConfirmationCode(confirmationCode);

        // Assert
        assertEquals(expectedBooking, actualBooking);
    }

    @Test
    void testFindByBookingConfirmationCode_WhenBookingDoesNotExist() {
        // Arrange
        String confirmationCode = "NONEXISTENT";
        when(bookingRepository.findByBookingConfirmationCode(confirmationCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookingService.findByBookingConfirmationCode(confirmationCode));
    }
}
