package kz.qonaqzhai.eventservice.repository;

import kz.qonaqzhai.eventservice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Примеры возможных методов запросов
    List<Event> findByOwnerUsername(String ownerUsername); // Найти все события пользователя
    List<Event> findByEventType(String eventType); // Найти события по типу
    List<Event> findByEventDateAfter(java.time.LocalDateTime date); // Найти события после определенной даты
}