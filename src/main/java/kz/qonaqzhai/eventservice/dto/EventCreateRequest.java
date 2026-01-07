package kz.qonaqzhai.eventservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

// DTO для создания нового события
public class EventCreateRequest {

    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    @NotBlank(message = "Event type is mandatory")
    private String eventType; // Например, "WEDDING", "CORPORATE"

    @NotNull(message = "Guest count is mandatory")
    @Positive(message = "Guest count must be positive")
    private Integer guestCount;

    @NotNull(message = "Budget is mandatory")
    @Positive(message = "Budget must be positive")
    private BigDecimal budget;

    @NotNull(message = "Event date is mandatory")
    private LocalDateTime eventDate;

    private String location;

    private Set<String> tags;

    // Constructors
    public EventCreateRequest() {}

    public EventCreateRequest(String title, String description, String eventType, Integer guestCount, BigDecimal budget, LocalDateTime eventDate, String location, Set<String> tags) {
        this.title = title;
        this.description = description;
        this.eventType = eventType;
        this.guestCount = guestCount;
        this.budget = budget;
        this.eventDate = eventDate;
        this.location = location;
        this.tags = tags;
    }

    // Getters and Setters
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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}