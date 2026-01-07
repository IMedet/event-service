package kz.qonaqzhai.eventservice.controller;

import kz.qonaqzhai.eventservice.dto.EventCreateRequest;
import kz.qonaqzhai.eventservice.dto.EventResponseDTO;
import kz.qonaqzhai.eventservice.service.IEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // Для получения имени пользователя из Security Context (через gateway)
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventsController {

    private final IEventService eventService;

    // Получить все события текущего пользователя
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents(Principal principal) {
        String username = principal.getName(); // Имя пользователя извлекается из токена (через gateway)
        log.info("GET /events requested for user: {}", username);
        List<EventResponseDTO> events = eventService.getAllEventsByOwner(username);
        return ResponseEntity.ok(events);
    }

    // Получить событие по ID
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable Long id) {
        log.info("GET /events/{} requested", id);
        EventResponseDTO event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    // Создать новое событие
    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventCreateRequest request, Principal principal) {
        String username = principal.getName(); // Имя пользователя извлекается из токена (через gateway)
        log.info("POST /events requested for user: {}", username);
        EventResponseDTO createdEvent = eventService.createEvent(request, username);
        return ResponseEntity.ok(createdEvent);
    }

    // Частично обновить событие по ID
    @PatchMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEventPartially(@PathVariable Long id, @RequestBody EventCreateRequest request, Principal principal) {
        String username = principal.getName(); // Имя пользователя извлекается из токена (через gateway)
        log.info("PATCH /events/{} requested for user: {}", id, username);
        EventResponseDTO updatedEvent = eventService.updateEventPartially(id, request, username);
        return ResponseEntity.ok(updatedEvent);
    }

    // Удалить событие по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        log.info("DELETE /events/{} requested", id);
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Получить события по типу
    @GetMapping("/type/{eventType}")
    public ResponseEntity<List<EventResponseDTO>> getEventsByType(@PathVariable String eventType) {
        log.info("GET /events/type/{} requested", eventType);
        List<EventResponseDTO> events = eventService.getEventsByType(eventType);
        return ResponseEntity.ok(events);
    }
}