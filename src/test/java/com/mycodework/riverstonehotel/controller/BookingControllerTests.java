package com.mycodework.riverstonehotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycodework.riverstonehotel.model.BookedRoom;
import com.mycodework.riverstonehotel.service.IBookingService;
import com.mycodework.riverstonehotel.service.IRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookingControllerTests {

    private MockMvc mockMvc;

    @Mock
    private IBookingService bookingService;

    @Mock
    private IRoomService roomService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    public void testGetAllBookings_AdminRole() throws Exception {
        List<BookedRoom> bookings = new ArrayList<>();
        when(bookingService.getAllBookings()).thenReturn(bookings);

        mockMvc.perform(get("/bookings/all-bookings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetBookingsByUserEmail_Success() throws Exception {
        String email = "test@example.com";
        List<BookedRoom> bookings = new ArrayList<>();
        when(bookingService.getBookingsByUserEmail(eq(email))).thenReturn(bookings);

        mockMvc.perform(get("/bookings/user/{email}/bookings", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testCancelBooking_Success() throws Exception {
        Long bookingId = 1L;
        doNothing().when(bookingService).cancelBooking(eq(bookingId));

        mockMvc.perform(delete("/bookings/booking/{bookingId}/delete", bookingId))
                .andExpect(status().isOk());
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
