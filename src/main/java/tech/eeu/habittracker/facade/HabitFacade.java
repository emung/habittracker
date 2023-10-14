package tech.eeu.habittracker.facade;

import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.eeu.habittracker.dto.HabitDto;
import tech.eeu.habittracker.mapper.HabitMapper;
import tech.eeu.habittracker.model.HabitModel;
import tech.eeu.habittracker.request.CreateHabitRequest;
import tech.eeu.habittracker.request.UpdateHabitRequest;
import tech.eeu.habittracker.service.HabitService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class HabitFacade {

    private final HabitService habitService;

    @Autowired
    private HabitMapper habitMapper;

    public List<HabitDto> getAllHabits() {
        List<HabitModel> habitModels = habitService.getAllHabits();
        return habitMapper.toDtoList(habitModels);
    }

    public HabitDto getHabitById(Long id) {
        HabitModel habitModel = habitService.getHabitById(id);
        return habitMapper.toDto(habitModel);
    }

    public HabitDto createHabit(CreateHabitRequest createHabitRequest) {
        HabitModel savedModel = habitService.createHabit(habitMapper.toModel(createHabitRequest));
        return habitMapper.toDto(savedModel);
    }

    public HabitDto updateHabit(Long id, UpdateHabitRequest updateHabitRequest) {
        HabitModel habitModel = habitMapper.toModel(updateHabitRequest);
        return habitMapper.toDto(habitService.updateHabitById(id, habitModel));
    }

    public void deleteHabitById(Long id) {
        habitService.deleteHabitById(id);
    }
}
