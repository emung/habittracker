package tech.eeu.habittracker.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.eeu.habittracker.dto.HabitDto;
import tech.eeu.habittracker.exception.HabitNotFoundException;
import tech.eeu.habittracker.facade.HabitFacade;
import tech.eeu.habittracker.request.CreateHabitRequest;
import tech.eeu.habittracker.request.UpdateHabitCategoryRequest;
import tech.eeu.habittracker.request.UpdateHabitRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/habits")
public class HabitController {

    private final HabitFacade habitFacade;

    @Operation(summary = "Get all existing habits")
    @GetMapping("/all")
    public ResponseEntity<List<HabitDto>> getAllHabits() {
        List<HabitDto> habitDtos = habitFacade.getAllHabits();
        return ResponseEntity.ok(habitDtos);
    }

    @Operation(summary = "Get a habit by id")
    @GetMapping("/{id}")
    public ResponseEntity<HabitDto> getHabitById(@PathVariable("id") Long id) {
        HabitDto habitDto = habitFacade.getHabitById(id);
        return ResponseEntity.ok(habitDto);
    }

    @Operation(summary = "Get all habits by category")
    @GetMapping
    public ResponseEntity<List<HabitDto>> getHabitsByCategory(@RequestParam(name = "category", defaultValue = "") String category) {
        List<HabitDto> habitDtos = habitFacade.getAllHabitsByCategory(category == null ? "" : category.trim());
        return ResponseEntity.ok(habitDtos);
    }

    @Operation(summary = "Create a new habit")
    @PostMapping
    public ResponseEntity<HabitDto> createHabit(@Valid @RequestBody CreateHabitRequest createHabitRequest) {
        HabitDto habitDto = habitFacade.createHabit(createHabitRequest);
        return ResponseEntity.ok(habitDto);
    }

    @Operation(summary = "Delete a habit by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHabitById(@PathVariable("id") Long id) {
        try {
            habitFacade.deleteHabitById(id);
            return new ResponseEntity<>("Habit was successfully deleted.", HttpStatus.OK);
        } catch (HabitNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Unable to delete Habit!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a habit by id")
    @PutMapping("/{id}")
    public ResponseEntity<HabitDto> updateHabit(@PathVariable("id") Long id, @Valid @RequestBody UpdateHabitRequest updateHabitRequest) {
        HabitDto habitDto = habitFacade.updateHabit(id, updateHabitRequest);
        return ResponseEntity.ok(habitDto);
    }

    @Operation(summary = "Update a habits category")
    @PutMapping("/{id}/category")
    public ResponseEntity<HabitDto> updateHabitCategory(@PathVariable("id") Long id, @Valid @RequestBody UpdateHabitCategoryRequest updateHabitCategoryRequest) {
        HabitDto habitDto = habitFacade.updateHabitCategory(id, updateHabitCategoryRequest.getCategory());
        return ResponseEntity.ok(habitDto);
    }


}
