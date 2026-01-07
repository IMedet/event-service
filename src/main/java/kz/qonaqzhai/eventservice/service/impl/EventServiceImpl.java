package kz.qonaqzhai.eventservice.service.impl;

import kz.qonaqzhai.eventservice.dto.EventCreateRequest;
import kz.qonaqzhai.eventservice.dto.EventResponseDTO;
import kz.qonaqzhai.eventservice.entity.Event;
import kz.qonaqzhai.eventservice.exception.EventNotFoundException;
import kz.qonaqzhai.eventservice.mapper.EventMapper;
import kz.qonaqzhai.eventservice.repository.EventRepository;
import kz.qonaqzhai.eventservice.service.IEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper; // Внедряем маппер

    @Override
    public List<EventResponseDTO> getAllEventsByOwner(String ownerUsername) {
        log.info("Fetching events for user: {}", ownerUsername);
        List<Event> events = eventRepository.findByOwnerUsername(ownerUsername);
        return events.stream()
                .map(eventMapper::toResponseDto) // Используем маппер
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDTO getEventById(Long id) {
        log.info("Fetching event with id: {}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
        return eventMapper.toResponseDto(event); // Используем маппер
    }

    @Override
    @Transactional
    public EventResponseDTO createEvent(EventCreateRequest request, String ownerUsername) {
        log.info("Creating event for user: {}", ownerUsername);
        Event event = eventMapper.toEntity(request); // Используем маппер для создания сущности
        event.setOwnerUsername(ownerUsername); // Владельца устанавливаем вручную

        Event savedEvent = eventRepository.save(event);
        log.info("Event created successfully with id: {}", savedEvent.getId());
        return eventMapper.toResponseDto(savedEvent); // Используем маппер для ответа
    }

    @Override
    @Transactional
    public void deleteEvent(Long id) {
        log.info("Deleting event with id: {}", id);
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
        log.info("Event deleted successfully with id: {}", id);
    }

    @Override
    @Transactional
    public EventResponseDTO updateEventPartially(Long id, EventCreateRequest request, String ownerUsername) {
        log.info("Updating event with id: {} for user: {}", id, ownerUsername);

        // Находим событие
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));

        // Проверяем, является ли текущий пользователь владельцем
        if (!event.getOwnerUsername().equals(ownerUsername)) {
            throw new RuntimeException("You do not have permission to update this event."); // В реальном проекте используйте кастомное исключение
        }

        // Обновляем только те поля, которые пришли в запросе (частичное обновление)
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getEventType() != null) {
            event.setEventType(request.getEventType());
        }
        if (request.getGuestCount() != null) {
            event.setGuestCount(request.getGuestCount());
        }
        if (request.getBudget() != null) {
            event.setBudget(request.getBudget());
        }
        if (request.getEventDate() != null) {
            event.setEventDate(request.getEventDate());
        }
        if (request.getLocation() != null) {
            event.setLocation(request.getLocation());
        }
        if (request.getTags() != null) {
            event.setTags(request.getTags()); // Полностью заменяем теги
        }

        Event updatedEvent = eventRepository.save(event);
        log.info("Event updated successfully with id: {}", updatedEvent.getId());
        return eventMapper.toResponseDto(updatedEvent);
    }

    @Override
    public List<EventResponseDTO> getEventsByType(String eventType) {
        log.info("Fetching events by type: {}", eventType);
        List<Event> events = eventRepository.findByEventType(eventType);
        return events.stream()
                .map(eventMapper::toResponseDto) // Используем маппер
                .collect(Collectors.toList());
    }
}