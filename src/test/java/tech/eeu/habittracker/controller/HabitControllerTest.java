package tech.eeu.habittracker.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import tech.eeu.habittracker.dto.HabitDto;
import tech.eeu.habittracker.exception.HabitNotFoundException;
import tech.eeu.habittracker.facade.HabitFacade;
import tech.eeu.habittracker.request.CreateHabitRequest;
import tech.eeu.habittracker.request.UpdateHabitRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
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

    @Test
    public void testCreateHabit() {
        CreateHabitRequest createHabitRequest = new CreateHabitRequest("Habit1", "Habit Description 1", "Main Habits");
        HabitDto expectedHabitDto = new HabitDto(1L, "Habit1", "Habit Description 1", "Main Habits");

        when(habitFacade.createHabit(createHabitRequest)).thenReturn(expectedHabitDto);
        HabitDto resultHabitDto = habitController.createHabit(createHabitRequest).getBody();

        assertNotNull(resultHabitDto);
        assertEquals(resultHabitDto, expectedHabitDto);
    }

    @Test
    public void testUpdateHabit() {
        UpdateHabitRequest updateHabitRequest = new UpdateHabitRequest("Habit1", "Habit Description 1", "Main Habits");
        HabitDto habitDto = new HabitDto(1L, "Habit1", "Habit Description 1", "Main Habits");

        when(habitFacade.updateHabit(1L, updateHabitRequest)).thenReturn(habitDto);
        HabitDto resultHabitDto = habitController.updateHabit(1L, updateHabitRequest).getBody();

        assertNotNull(resultHabitDto);
        assertEquals(habitDto, resultHabitDto);
    }

    @Test
    public void testDeleteHabitResultOk() {
        doNothing().when(habitFacade).deleteHabitById(1L);
        Object obj = habitController.deleteHabitById(1L);
        assertNotNull(obj);

        ResponseEntity<String> response = (ResponseEntity<String>) obj;
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("Habit was successfully deleted.", response.getBody());
    }

    @Test
    public void testDeleteHabitThrowHabitNotFoundException() {
        HabitNotFoundException habitNotFoundException = new HabitNotFoundException("Can not find Habit with id 99!");
        doThrow(habitNotFoundException).when(habitFacade).deleteHabitById(99L);
        Object obj = habitController.deleteHabitById(99L);
        assertNotNull(obj);

        ResponseEntity<String> response = (ResponseEntity<String>) obj;
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals(habitNotFoundException.getMessage(), response.getBody());
    }

    @Disabled("Disabled for further investigation")
    @Test
    public void testDeleteHabitThrowException() {
        Exception exception = new Exception("Unable to delete Habit!");
        doThrow(exception).when(habitFacade).deleteHabitById(99L);
        ResponseEntity<?> response = habitController.deleteHabitById(99L);
        assertNotNull(response);

        assertEquals(HttpStatusCode.valueOf(500), response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
    }
}