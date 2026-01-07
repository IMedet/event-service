package kz.qonaqzhai.eventservice.service;

import kz.qonaqzhai.eventservice.dto.EventCreateRequest;
import kz.qonaqzhai.eventservice.dto.EventResponseDTO;
import kz.qonaqzhai.eventservice.entity.Event;
import java.util.List;

public interface IEventService {

    List<EventResponseDTO> getAllEventsByOwner(String ownerUsername); // Получить все события пользователя
    EventResponseDTO getEventById(Long id); // Получить событие по ID
    EventResponseDTO createEvent(EventCreateRequest request, String ownerUsername); // Создать новое событие
    EventResponseDTO updateEventPartially(Long id, EventCreateRequest request, String ownerUsername); // Частично обновить событие
    void deleteEvent(Long id); // Удалить событие
    List<EventResponseDTO> getEventsByType(String eventType); // Получить события по типу
}