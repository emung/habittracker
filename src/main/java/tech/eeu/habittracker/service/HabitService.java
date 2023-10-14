package tech.eeu.habittracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.eeu.habittracker.exception.HabitNotFoundException;
import tech.eeu.habittracker.model.HabitModel;
import tech.eeu.habittracker.repository.HabitRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HabitService {

    private final HabitRepository habitRepository;

    public List<HabitModel> getAllHabits() {
        return habitRepository.findAll();
    }

    public HabitModel getHabitById(Long id) {
        return habitRepository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException("Can not find Habit with id %d!".formatted(id)));
    }

    public HabitModel createHabit(HabitModel habitModel) {
        return habitRepository.save(habitModel);
    }

    public void deleteHabitById(Long id) {
        HabitModel habitModel = habitRepository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException("Can not find Habit with id %d!".formatted(id)));
        habitRepository.delete(habitModel);
    }

    public HabitModel updateHabitById(Long id, HabitModel updateHabitModel) {
        HabitModel habitModel = habitRepository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException("Can not find Habit with id %d!".formatted(id)));
        updateHabitModel.setId(habitModel.getId());
        return habitRepository.save(habitModel);
    }
}

