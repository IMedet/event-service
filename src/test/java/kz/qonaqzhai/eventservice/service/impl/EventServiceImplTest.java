package kz.qonaqzhai.eventservice.service.impl;

import kz.qonaqzhai.eventservice.dto.EventCreateRequest;
import kz.qonaqzhai.eventservice.dto.EventResponseDTO;
import kz.qonaqzhai.eventservice.entity.Event;
import kz.qonaqzhai.eventservice.exception.EventNotFoundException;
import kz.qonaqzhai.eventservice.mapper.EventMapper;
import kz.qonaqzhai.eventservice.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void getAllEventsByOwner_mapsEntitiesToDtos() {
        String owner = "alice";

        Event e1 = new Event("t1", "WEDDING", 10, BigDecimal.TEN, LocalDateTime.now(), owner);
        e1.setId(1L);
        Event e2 = new Event("t2", "CORPORATE", 20, BigDecimal.ONE, LocalDateTime.now().plusDays(1), owner);
        e2.setId(2L);

        when(eventRepository.findByOwnerUsername(owner)).thenReturn(List.of(e1, e2));

        EventResponseDTO d1 = new EventResponseDTO();
        d1.setId(1L);
        EventResponseDTO d2 = new EventResponseDTO();
        d2.setId(2L);

        when(eventMapper.toResponseDto(e1)).thenReturn(d1);
        when(eventMapper.toResponseDto(e2)).thenReturn(d2);

        List<EventResponseDTO> result = eventService.getAllEventsByOwner(owner);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(eventRepository).findByOwnerUsername(owner);
        verify(eventMapper).toResponseDto(e1);
        verify(eventMapper).toResponseDto(e2);
    }

    @Test
    void getEventById_returnsMappedDto_whenEventExists() {
        Long id = 10L;
        Event e = new Event();
        e.setId(id);
        when(eventRepository.findById(id)).thenReturn(Optional.of(e));

        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(id);
        when(eventMapper.toResponseDto(e)).thenReturn(dto);

        EventResponseDTO result = eventService.getEventById(id);

        assertEquals(id, result.getId());
        verify(eventRepository).findById(id);
        verify(eventMapper).toResponseDto(e);
    }

    @Test
    void getEventById_throwsEventNotFoundException_whenMissing() {
        Long id = 999L;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.getEventById(id));
        verify(eventRepository).findById(id);
        verifyNoInteractions(eventMapper);
    }

    @Test
    void createEvent_setsOwnerAndSaves_andReturnsMappedDto() {
        String owner = "bob";
        EventCreateRequest request = new EventCreateRequest(
                "title",
                "desc",
                "BIRTHDAY",
                5,
                BigDecimal.valueOf(123.45),
                LocalDateTime.now().plusDays(3),
                "Almaty",
                Set.of("t")
        );

        Event mapped = new Event();
        when(eventMapper.toEntity(request)).thenReturn(mapped);

        Event saved = new Event();
        saved.setId(77L);
        when(eventRepository.save(any(Event.class))).thenReturn(saved);

        EventResponseDTO response = new EventResponseDTO();
        response.setId(77L);
        when(eventMapper.toResponseDto(saved)).thenReturn(response);

        EventResponseDTO result = eventService.createEvent(request, owner);

        assertEquals(77L, result.getId());

        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository).save(captor.capture());
        assertEquals(owner, captor.getValue().getOwnerUsername());

        verify(eventMapper).toEntity(request);
        verify(eventMapper).toResponseDto(saved);
    }

    @Test
    void deleteEvent_throwsEventNotFoundException_whenMissing() {
        Long id = 5L;
        when(eventRepository.existsById(id)).thenReturn(false);

        assertThrows(EventNotFoundException.class, () -> eventService.deleteEvent(id));

        verify(eventRepository).existsById(id);
        verify(eventRepository, never()).deleteById(any());
    }

    @Test
    void deleteEvent_deletes_whenExists() {
        Long id = 5L;
        when(eventRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> eventService.deleteEvent(id));

        verify(eventRepository).existsById(id);
        verify(eventRepository).deleteById(id);
    }

    @Test
    void updateEventPartially_updatesOnlyProvidedFields() {
        Long id = 1L;
        String owner = "alice";

        Event existing = new Event();
        existing.setId(id);
        existing.setOwnerUsername(owner);
        existing.setTitle("old");
        existing.setDescription("oldDesc");
        existing.setEventType("WEDDING");
        existing.setGuestCount(10);
        existing.setBudget(BigDecimal.TEN);
        existing.setEventDate(LocalDateTime.now());
        existing.setLocation("oldLoc");
        existing.setTags(Set.of("old"));

        when(eventRepository.findById(id)).thenReturn(Optional.of(existing));

        EventCreateRequest patch = new EventCreateRequest();
        patch.setTitle("newTitle");
        patch.setGuestCount(42);
        patch.setTags(Set.of("new1", "new2"));

        Event saved = existing;
        when(eventRepository.save(existing)).thenReturn(saved);

        EventResponseDTO response = new EventResponseDTO();
        response.setId(id);
        when(eventMapper.toResponseDto(saved)).thenReturn(response);

        EventResponseDTO result = eventService.updateEventPartially(id, patch, owner);

        assertEquals(id, result.getId());
        assertEquals("newTitle", existing.getTitle());
        assertEquals(42, existing.getGuestCount());
        assertEquals(Set.of("new1", "new2"), existing.getTags());
        assertEquals("oldDesc", existing.getDescription());

        verify(eventRepository).findById(id);
        verify(eventRepository).save(existing);
        verify(eventMapper).toResponseDto(saved);
    }

    @Test
    void updateEventPartially_throws_whenNotOwner() {
        Long id = 1L;
        Event existing = new Event();
        existing.setId(id);
        existing.setOwnerUsername("owner");

        when(eventRepository.findById(id)).thenReturn(Optional.of(existing));

        EventCreateRequest patch = new EventCreateRequest();
        patch.setTitle("newTitle");

        assertThrows(RuntimeException.class, () -> eventService.updateEventPartially(id, patch, "intruder"));

        verify(eventRepository).findById(id);
        verify(eventRepository, never()).save(any());
        verifyNoInteractions(eventMapper);
    }

    @Test
    void getEventsByType_mapsEntitiesToDtos() {
        String type = "WEDDING";
        Event e = new Event();
        e.setId(3L);
        when(eventRepository.findByEventType(type)).thenReturn(List.of(e));

        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(3L);
        when(eventMapper.toResponseDto(e)).thenReturn(dto);

        List<EventResponseDTO> result = eventService.getEventsByType(type);

        assertEquals(1, result.size());
        assertEquals(3L, result.get(0).getId());
        verify(eventRepository).findByEventType(type);
        verify(eventMapper).toResponseDto(e);
    }
}
