package kz.qonaqzhai.eventservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

// DTO для ответа с информацией о событии
public class EventResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String eventType;
    private Integer guestCount;
    private BigDecimal budget;
    private LocalDateTime eventDate;
    private String location;
    private String ownerUsername;
    private Set<String> tags;

    // Constructors
    public EventResponseDTO() {}

    public EventResponseDTO(Long id, String title, String description, String eventType, Integer guestCount, BigDecimal budget, LocalDateTime eventDate, String location, String ownerUsername, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventType = eventType;
        this.guestCount = guestCount;
        this.budget = budget;
        this.eventDate = eventDate;
        this.location = location;
        this.ownerUsername = ownerUsername;
        this.tags = tags;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}