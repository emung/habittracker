package tech.eeu.habittracker.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import tech.eeu.habittracker.dto.HabitDto;
import tech.eeu.habittracker.model.HabitModel;
import tech.eeu.habittracker.request.CreateHabitRequest;
import tech.eeu.habittracker.request.UpdateHabitRequest;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.FIELD)
public interface HabitMapper {

    @Mapping(target = "category", source = "category", defaultValue = "")
    HabitDto toDto(HabitModel habitModel);

    List<HabitDto> toDtoList(List<HabitModel> habitModels);

    @Mapping(target = "id", ignore = true)
    HabitModel toModel(CreateHabitRequest createHabitRequest);

    @Mapping(target = "id", ignore = true)
    HabitModel toModel(UpdateHabitRequest updateHabitRequest);
}
