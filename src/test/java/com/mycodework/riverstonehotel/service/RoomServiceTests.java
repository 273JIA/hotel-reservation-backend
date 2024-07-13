package com.mycodework.riverstonehotel.service;

import com.mycodework.riverstonehotel.model.Room;
import com.mycodework.riverstonehotel.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RoomServiceTests {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewRoom() throws SQLException, IOException {
        // Mock data
        MockMultipartFile mockFile = new MockMultipartFile("photo", "test.txt", "text/plain", "Mock photo content".getBytes());
        String roomType = "Standard";
        BigDecimal roomPrice = BigDecimal.valueOf(100);

        Room savedRoom = new Room();
        savedRoom.setId(1L);
        savedRoom.setRoomType(roomType);
        savedRoom.setRoomPrice(roomPrice);
        savedRoom.setPhoto(new SerialBlob(mockFile.getBytes()));

        // Mock repository behavior
        when(roomRepository.save(any(Room.class))).thenReturn(savedRoom);

        // Call the service method
        Room createdRoom = roomService.addNewRoom(mockFile, roomType, roomPrice);

        // Verify the result
        assertNotNull(createdRoom);
        assertEquals(roomType, createdRoom.getRoomType());
        assertEquals(roomPrice, createdRoom.getRoomPrice());
        assertNotNull(createdRoom.getPhoto());

        // Verify that save method was called once
        verify(roomRepository, times(1)).save(any(Room.class));
    }
}
