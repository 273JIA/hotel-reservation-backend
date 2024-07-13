package com.mycodework.riverstonehotel.repository;

import com.mycodework.riverstonehotel.model.BookedRoom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingRepositoryTests {

    @Mock
    private BookingRepository bookingRepository;

    @Test
    public void testFindByRoomId() {
        // Arrange
        Long roomId = 1L;
        List<BookedRoom> expectedBookings = Arrays.asList(new BookedRoom(), new BookedRoom());
        when(bookingRepository.findByRoomId(roomId)).thenReturn(expectedBookings);

        // Act
        List<BookedRoom> bookings = bookingRepository.findByRoomId(roomId);

        // Assert
        assertEquals(expectedBookings.size(), bookings.size());
        assertEquals(expectedBookings, bookings);
    }

    @Test
    public void testFindByBookingConfirmationCode() {
        // Arrange
        String confirmationCode = "ABC123";
        BookedRoom expectedBooking = new BookedRoom();
        when(bookingRepository.findByBookingConfirmationCode(confirmationCode)).thenReturn(Optional.of(expectedBooking));

        // Act
        Optional<BookedRoom> bookingOptional = bookingRepository.findByBookingConfirmationCode(confirmationCode);

        // Assert
        assertEquals(expectedBooking, bookingOptional.orElse(null));
    }

    @Test
    public void testFindByGuestEmail() {
        // Arrange
        String guestEmail = "test@example.com";
        List<BookedRoom> expectedBookings = Arrays.asList(new BookedRoom(), new BookedRoom());
        when(bookingRepository.findByGuestEmail(guestEmail)).thenReturn(expectedBookings);

        // Act
        List<BookedRoom> bookings = bookingRepository.findByGuestEmail(guestEmail);

        // Assert
        assertEquals(expectedBookings.size(), bookings.size());
        assertEquals(expectedBookings, bookings);
    }
}
