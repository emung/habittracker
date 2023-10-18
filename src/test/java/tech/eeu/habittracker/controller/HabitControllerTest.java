package tech.eeu.habittracker.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.eeu.habittracker.dto.HabitDto;
import tech.eeu.habittracker.facade.HabitFacade;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitControllerTest {

    @InjectMocks
    HabitController habitController;

    @Mock
    HabitFacade habitFacade;

    @Test
    public void testGetAllHabits() {
        HabitDto habitDto1 = new HabitDto(1L, "Habit_1", "Habit_Description_1", "Habit_Category_1");
        HabitDto habitDto2 = new HabitDto(2L, "Habit_2", "Habit_Description_2", "Habit_Category_2");
        List<HabitDto> habitDtoList = new ArrayList<>(List.of(habitDto1, habitDto2));

        when(habitFacade.getAllHabits()).thenReturn(habitDtoList);
        List<HabitDto> resultList = habitController.getAllHabits().getBody();

        assertNotNull(resultList);
        assertEquals(habitDtoList.size(), resultList.size());
        assertTrue(resultList.contains(habitDto1));
        assertTrue(resultList.contains(habitDto2));
        assertEquals(habitDto1, resultList.get(0));
        assertEquals(habitDto2, resultList.get(1));
    }

    @Test
    public void testGetHabitById() {
        HabitDto habitDto = new HabitDto(5L, "Learn Java", "Learn Java every day", "Programming");

        when(habitFacade.getHabitById(5L)).thenReturn(habitDto);
        HabitDto resultHabitDto = habitController.getHabitById(5L).getBody();

        assertNotNull(resultHabitDto);
        assertEquals(habitDto.getId(), resultHabitDto.getId());
        assertEquals(habitDto.getName(), resultHabitDto.getName());
        assertEquals(habitDto.getDescription(), resultHabitDto.getDescription());
        assertEquals(habitDto.getCategory(), resultHabitDto.getCategory());
    }

    @Test
    public void testGetHabitsByCategory() {
        final String CATEGORY_STRING = "Category 1";
        HabitDto habitDto1 = new HabitDto(1L, "Habit_1", "Habit_Description_1", "Category 1");
        HabitDto habitDto2 = new HabitDto(2L, "Habit_2", "Habit_Description_2", "Category 1");
        List<HabitDto> habitDtoList = new ArrayList<>(List.of(habitDto1, habitDto2));

        when(habitFacade.getAllHabitsByCategory(CATEGORY_STRING)).thenReturn(habitDtoList);
        List<HabitDto> resultList = habitController.getHabitsByCategory(CATEGORY_STRING).getBody();

        assertNotNull(resultList);
        assertEquals(habitDtoList.size(), resultList.size());
        assertTrue(resultList.stream().allMatch(habitDto -> CATEGORY_STRING.equals(habitDto.getCategory())));
    }

    @Test
    public void testGetHabitsByCategoryWhenCategoryIsNull() {
        HabitDto habitDto1 = new HabitDto(1L, "Habit_1", "Habit_Description_1", "Category 1");
        HabitDto habitDto2 = new HabitDto(2L, "Habit_2", "Habit_Description_2", "Category 2");
        List<HabitDto> habitDtoList = new ArrayList<>(List.of(habitDto1, habitDto2));

        when(habitFacade.getAllHabitsByCategory("")).thenReturn(habitDtoList);
        List<HabitDto> resultList = habitController.getHabitsByCategory(null).getBody();

        assertNotNull(resultList);
        assertEquals(habitDtoList.size(), resultList.size());
        assertNotEquals(resultList.get(0).getCategory(), resultList.get(1).getCategory());
    }

}
