package kz.qonaqzhai.eventservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // Название события

    @Column(length = 1000) // Пример ограничения длины
    private String description; // Описание

    @Column(nullable = false)
    private String eventType; // Тип события (например, "WEDDING", "CORPORATE", "BIRTHDAY")

    @Column(nullable = false)
    private Integer guestCount; // Количество гостей

    @Column(precision = 10, scale = 2, nullable = false) // Для хранения денежных сумм
    private BigDecimal budget; // Бюджет

    @Column(nullable = false)
    private LocalDateTime eventDate; // Дата и время события

    private String location; // Место проведения

    @Column(nullable = false)
    private String ownerUsername; // Имя владельца события (связь через внешнюю систему или username)

    @ElementCollection(fetch = FetchType.LAZY) // Пример коллекции, например, список тегов
    @CollectionTable(name = "event_tags", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    // Конструктор по умолчанию необходим для JPA
    public Event() {}

    // Можно добавить конструктор с обязательными полями
    public Event(String title, String eventType, Integer guestCount, BigDecimal budget, LocalDateTime eventDate, String ownerUsername) {
        this.title = title;
        this.eventType = eventType;
        this.guestCount = guestCount;
        this.budget = budget;
        this.eventDate = eventDate;
        this.ownerUsername = ownerUsername;
    }
}