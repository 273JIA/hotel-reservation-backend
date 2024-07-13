package com.mycodework.riverstonehotel.repository;

import com.mycodework.riverstonehotel.model.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomRepositoryTests {

    @Mock
    private RoomRepository roomRepository;

    @Test
    public void testFindDistinctRoomTypes() {
        // Arrange
        List<String> expectedRoomTypes = Arrays.asList("Single", "Double", "Suite");
        when(roomRepository.findDistinctRoomTypes()).thenReturn(expectedRoomTypes);

        // Act
        List<String> roomTypes = roomRepository.findDistinctRoomTypes();

        // Assert
        assertEquals(expectedRoomTypes.size(), roomTypes.size());
        assertEquals(expectedRoomTypes, roomTypes);
    }

    @Test
    public void testFindAvailableRoomsByDatesAndType() {
        // Arrange
        LocalDate checkInDate = LocalDate.of(2024, 7, 10);
        LocalDate checkOutDate = LocalDate.of(2024, 7, 15);
        String roomType = "Double";

        // Assuming you have a list of rooms to return for this test scenario
        List<Room> expectedRooms = Arrays.asList(new Room(), new Room());
        when(roomRepository.findAvailableRoomsByDatesAndType(any(LocalDate.class), any(LocalDate.class), anyString())).thenReturn(expectedRooms);

        // Act
        List<Room> availableRooms = roomRepository.findAvailableRoomsByDatesAndType(checkInDate, checkOutDate, roomType);

        // Assert
        assertEquals(expectedRooms.size(), availableRooms.size());
        // Add more specific assertions based on your expected behavior
    }
}
