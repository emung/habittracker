package tech.eeu.habittracker.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.eeu.habittracker.dto.HabitDto;
import tech.eeu.habittracker.mapper.HabitMapper;
import tech.eeu.habittracker.model.HabitModel;
import tech.eeu.habittracker.model.TargetPeriod;
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

    public List<HabitDto> getAllHabitsByCategory(String category) {
        List<HabitModel> habitModels = habitService.getHabitsByCategory(category);
        return habitMapper.toDtoList(habitModels);
    }

    public HabitDto setHabitTarget(Long habitId, Integer target, TargetPeriod targetPeriod) {
        HabitModel habitModel = habitService.setTargetForHabit(habitId, target, targetPeriod);
        return habitMapper.toDto(habitModel);
    }

    public HabitDto incrementHabitTargetProgress(Long habitId, Integer incrementBy) {
        HabitModel habitModel = habitService.incrementTargetProgress(habitId, incrementBy);
        return habitMapper.toDto(habitModel);
    }

    public HabitDto decrementHabitTargetProgress(Long habitId, Integer decrementBy) {
        HabitModel habitModel = habitService.decrementTargetProgress(habitId, decrementBy);
        return habitMapper.toDto(habitModel);
    }
}
