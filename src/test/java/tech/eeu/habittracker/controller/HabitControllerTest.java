package tech.eeu.habittracker.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import tech.eeu.habittracker.dto.HabitDto;
import tech.eeu.habittracker.facade.HabitFacade;
import tech.eeu.habittracker.model.TargetPeriod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class HabitControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    HabitFacade habitFacade;

    private static List<HabitDto> HABIT_DTOS;

    private static HabitDto HABIT_DTO;

    private static final String CATEGORY = "Main category2";

    @BeforeEach
    public void initializeWebAppContext() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @BeforeAll
    public static void initializeHabitDtoList() {
        HABIT_DTOS = new ArrayList<>(List.of(
                new HabitDto(1L, "Habit name1", "Habit description1", "Main category1", 3, TargetPeriod.DAILY, 1),
                new HabitDto(2L, "Habit name2", "Habit description2", "Main category1", 3, TargetPeriod.DAILY, 1),
                new HabitDto(3L, "Habit name3", "Habit description3", "Main category2", 3, TargetPeriod.DAILY, 1),
                new HabitDto(4L, "Habit name4", "Habit description4", "Main category2", 3, TargetPeriod.DAILY, 1)
        ));

        HABIT_DTO = new HabitDto(1L, "Habit name1", "Habit description1", "Main category1", 3, TargetPeriod.DAILY, 1);
    }

    @Test
    @DisplayName("GET all habits")
    public void shouldReturnAllHabits() {
        when(habitFacade.getAllHabits()).thenReturn(HABIT_DTOS);

        MockMvcResponse response = RestAssuredMockMvc.get("/habits/all");
        response.then().status(HttpStatus.OK);

        List<HabitDto> habitDtos = response.jsonPath().getList("", HabitDto.class);
        assertNotNull(habitDtos);
        assertEquals(habitDtos.size(), HABIT_DTOS.size());
        assertEquals(habitDtos.get(0), HABIT_DTOS.get(0));
        assertEquals(habitDtos.get(1), HABIT_DTOS.get(1));
    }

    @Test
    @DisplayName("GET habit by id")
    public void shouldReturnHabitWithId() {
        when(habitFacade.getHabitById(1L)).thenReturn(HABIT_DTO);

        MockMvcResponse response = RestAssuredMockMvc.get("/habits/1");
        response.then().status(HttpStatus.OK);

        HabitDto habitDto = response.as(HabitDto.class);
        assertNotNull(habitDto);
        assertEquals(HABIT_DTO.getId(), habitDto.getId());
        assertEquals(HABIT_DTO.getName(), habitDto.getName());
        assertEquals(HABIT_DTO.getDescription(), habitDto.getDescription());
    }

    @Test
    @DisplayName("GET habits by category")
    public void whenGetAllHabitsByCategoryThenReturnHabitsMatchingCategory() {
        when(habitFacade.getAllHabitsByCategory(CATEGORY)).thenReturn(HABIT_DTOS.stream()
                .filter(habit -> habit.getCategory().equals(CATEGORY))
                .toList());

        MockMvcResponse response = RestAssuredMockMvc.given()
                .queryParam("category", CATEGORY)
                .get("/habits");
        response.then().status(HttpStatus.OK);

        List<HabitDto> habitDtos = response.jsonPath().getList("", HabitDto.class);
        assertNotNull(habitDtos);
    }
}
