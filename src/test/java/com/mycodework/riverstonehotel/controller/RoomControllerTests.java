package com.mycodework.riverstonehotel.controller;

import com.mycodework.riverstonehotel.service.IRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoomControllerTests {

    private MockMvc mockMvc;

    @Mock
    private IRoomService roomService;

    @InjectMocks
    private RoomController roomController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
    }


    @Test
    public void testDeleteRoom() throws Exception {
        Long roomId = 1L;
        doNothing().when(roomService).deleteRoom(eq(roomId));

        mockMvc.perform(delete("/rooms/delete/room/{roomId}", roomId))
                .andExpect(status().isNoContent());
    }
}
