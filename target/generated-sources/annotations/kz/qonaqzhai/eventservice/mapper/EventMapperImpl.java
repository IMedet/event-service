package kz.qonaqzhai.eventservice.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import kz.qonaqzhai.eventservice.dto.EventCreateRequest;
import kz.qonaqzhai.eventservice.dto.EventResponseDTO;
import kz.qonaqzhai.eventservice.entity.Event;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-07T21:56:53+0500",
    comments = "version: 1.5.0.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public Event toEntity(EventCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Event event = new Event();

        event.setBudget( request.getBudget() );
        event.setDescription( request.getDescription() );
        event.setEventDate( request.getEventDate() );
        event.setEventType( request.getEventType() );
        event.setGuestCount( request.getGuestCount() );
        event.setLocation( request.getLocation() );
        Set<String> set = request.getTags();
        if ( set != null ) {
            event.setTags( new LinkedHashSet<String>( set ) );
        }
        event.setTitle( request.getTitle() );

        return event;
    }

    @Override
    public EventResponseDTO toResponseDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventResponseDTO eventResponseDTO = new EventResponseDTO();

        eventResponseDTO.setId( event.getId() );
        eventResponseDTO.setTitle( event.getTitle() );
        eventResponseDTO.setDescription( event.getDescription() );
        eventResponseDTO.setEventType( event.getEventType() );
        eventResponseDTO.setGuestCount( event.getGuestCount() );
        eventResponseDTO.setBudget( event.getBudget() );
        eventResponseDTO.setEventDate( event.getEventDate() );
        eventResponseDTO.setLocation( event.getLocation() );
        eventResponseDTO.setOwnerUsername( event.getOwnerUsername() );
        Set<String> set = event.getTags();
        if ( set != null ) {
            eventResponseDTO.setTags( new LinkedHashSet<String>( set ) );
        }

        return eventResponseDTO;
    }
}
