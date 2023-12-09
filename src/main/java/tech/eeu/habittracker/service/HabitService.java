package tech.eeu.habittracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.eeu.habittracker.exception.CreateHabitException;
import tech.eeu.habittracker.exception.HabitNotFoundException;
import tech.eeu.habittracker.exception.HabitTargetProgressDecrementException;
import tech.eeu.habittracker.exception.HabitTargetProgressIncrementException;
import tech.eeu.habittracker.model.HabitModel;
import tech.eeu.habittracker.model.TargetPeriod;
import tech.eeu.habittracker.repository.HabitRepository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
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
        if (habitModel.getTargetProgress() > habitModel.getTarget()) {
            throw new CreateHabitException("Habit can not be saved! Actual target progress can not be higher than the target.");
        }

        habitModel.setName(habitModel.getName().trim());
        habitModel.setDescription(habitModel.getDescription().trim());
        return habitRepository.save(habitModel);
    }

    public void deleteHabitById(Long id) {
        HabitModel habitModel = this.getHabitById(id);
        habitRepository.delete(habitModel);
    }

    public HabitModel updateHabitById(Long id, HabitModel updateHabitModel) {
        HabitModel existingHabitModel = this.getHabitById(id);
        existingHabitModel.setName(updateHabitModel.getName().trim());
        existingHabitModel.setDescription(updateHabitModel.getDescription().trim());
        existingHabitModel.setCategory(updateHabitModel.getCategory().trim());
        return habitRepository.save(existingHabitModel);
    }

    public List<HabitModel> getHabitsByCategory(String category) {
        return habitRepository.findAllByCategory(category);
    }

    public HabitModel setTargetForHabit(Long habitId, Integer target, TargetPeriod targetPeriod) {
        HabitModel habitModel = this.getHabitById(habitId);
        habitModel.setTarget(target);
        habitModel.setTargetProgress(0);
        habitModel.setTargetPeriod(targetPeriod);
        return habitRepository.save(habitModel);
    }

    public HabitModel incrementTargetProgress(Long habitId, Integer incrementBy) {
        HabitModel habitModel = this.getHabitById(habitId);
        incrementBy = incrementBy == null || incrementBy < 0 ? 0 : incrementBy;

        if (habitModel.getTarget() == 0) {
            throw new HabitTargetProgressIncrementException("The habit target is not set yet! (value = 0)");
        } else if (Objects.equals(habitModel.getTargetProgress(), habitModel.getTarget())) {
            throw new HabitTargetProgressIncrementException("Can not increment habit target progress because the progress is already at maximum!");
        } else if (habitModel.getTargetProgress() + incrementBy > habitModel.getTarget()) {
            throw new HabitTargetProgressIncrementException("Can not increment habit target progress by %d because it would be higher than the habit target!".formatted(incrementBy));
        }

        habitModel.setTargetProgress(habitModel.getTargetProgress() + incrementBy);
        return habitRepository.save(habitModel);
    }

    public HabitModel decrementTargetProgress(Long habitId, Integer decrementBy) {
        HabitModel habitModel = this.getHabitById(habitId);
        decrementBy = decrementBy == null || decrementBy < 0 ? 0 : decrementBy;

        if (habitModel.getTarget() == 0) {
            throw new HabitTargetProgressDecrementException("The habit target is not set yet! (value = 0)");
        } else if (habitModel.getTargetProgress() - decrementBy < 0) {
            throw new HabitTargetProgressDecrementException("Can not decrement habit target progress by %d because it would be lower than 0!".formatted(decrementBy));
        }

        habitModel.setTargetProgress(habitModel.getTargetProgress() - decrementBy);
        return habitRepository.save(habitModel);
    }

}

