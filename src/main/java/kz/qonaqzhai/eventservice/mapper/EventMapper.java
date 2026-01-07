package kz.qonaqzhai.eventservice.mapper;

import kz.qonaqzhai.eventservice.dto.EventCreateRequest;
import kz.qonaqzhai.eventservice.dto.EventResponseDTO;
import kz.qonaqzhai.eventservice.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // Указывает, что маппер должен быть бином Spring
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    // Маппинг из DTO запроса в Entity (например, для создания)
    @Mapping(target = "id", ignore = true) // ID генерируется БД, игнорируем при маппинге
    @Mapping(target = "ownerUsername", ignore = true) // Владелец устанавливается вручную в сервисе
    Event toEntity(EventCreateRequest request);

    // Маппинг из Entity в DTO ответа
    EventResponseDTO toResponseDto(Event event);
}