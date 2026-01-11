package kz.qonaqzhai.eventservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.qonaqzhai.eventservice.config.SecurityConfig;
import kz.qonaqzhai.eventservice.dto.EventCreateRequest;
import kz.qonaqzhai.eventservice.dto.EventResponseDTO;
import kz.qonaqzhai.eventservice.service.IEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventsController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = "security.internal-gateway.enabled=false")
class EventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IEventService eventService;

    @Test
    void getAllEvents_returnsOkAndList() throws Exception {
        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(1L);
        when(eventService.getAllEventsByOwner("alice")).thenReturn(List.of(dto));

        mockMvc.perform(get("/events").with(user("alice")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getEventById_returnsOk() throws Exception {
        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(10L);
        when(eventService.getEventById(10L)).thenReturn(dto);

        mockMvc.perform(get("/events/10").with(user("alice")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void createEvent_returnsOk() throws Exception {
        EventCreateRequest request = new EventCreateRequest(
                "title",
                "desc",
                "WEDDING",
                10,
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(1),
                "loc",
                Set.of("t")
        );

        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(5L);
        when(eventService.createEvent(any(EventCreateRequest.class), eq("alice"))).thenReturn(dto);

        mockMvc.perform(post("/events")
                        .with(user("alice"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void updateEventPartially_returnsOk() throws Exception {
        EventCreateRequest request = new EventCreateRequest();
        request.setTitle("newTitle");

        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(2L);
        when(eventService.updateEventPartially(eq(2L), any(EventCreateRequest.class), eq("alice"))).thenReturn(dto);

        mockMvc.perform(patch("/events/2")
                        .with(user("alice"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void deleteEvent_returnsNoContent() throws Exception {
        doNothing().when(eventService).deleteEvent(3L);

        mockMvc.perform(delete("/events/3").with(user("alice")))
                .andExpect(status().isNoContent());
    }

    @Test
    void getEventsByType_returnsOkAndList() throws Exception {
        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(9L);
        when(eventService.getEventsByType("WEDDING")).thenReturn(List.of(dto));

        mockMvc.perform(get("/events/type/WEDDING").with(user("alice")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(9));
    }
}
