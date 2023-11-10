package tech.eeu.habittracker.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
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
import tech.eeu.habittracker.request.CreateHabitRequest;
import tech.eeu.habittracker.request.UpdateHabitRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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

    private static List<HabitDto> HABIT_DTOS_WITH_CATEGORY;

    private static HabitDto HABIT_DTO;

    private static HabitDto UPDATED_HABIT_DTO;

    private static final String CATEGORY = "Main category2";

    private static final String BASE_PATH = "/habits";

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

        HABIT_DTOS_WITH_CATEGORY = new ArrayList<>(List.of(
                new HabitDto(3L, "Habit name3", "Habit description3", "Main category2", 3, TargetPeriod.DAILY, 1),
                new HabitDto(4L, "Habit name4", "Habit description4", "Main category2", 3, TargetPeriod.DAILY, 1)
        ));

        HABIT_DTO = new HabitDto(1L, "Habit name1", "Habit description1", "Main category1", 3, TargetPeriod.DAILY, 1);

        UPDATED_HABIT_DTO = new HabitDto(1L, "Updated Habit name1", "Updated Habit description1", "Main category1", 3, TargetPeriod.DAILY, 1);
    }

    @Test
    @DisplayName("Get all habits")
    public void shouldReturnAllHabits() {
        when(habitFacade.getAllHabits()).thenReturn(HABIT_DTOS);

        MockMvcResponse response = RestAssuredMockMvc.get(BASE_PATH + "/all");
        verify(habitFacade).getAllHabits();
        response.then().status(HttpStatus.OK);

        List<HabitDto> habitDtos = response.jsonPath().getList("", HabitDto.class);
        assertNotNull(habitDtos);
        assertEquals(habitDtos.size(), HABIT_DTOS.size());
        assertEquals(habitDtos.get(0), HABIT_DTOS.get(0));
        assertEquals(habitDtos.get(1), HABIT_DTOS.get(1));
    }

    @Test
    @DisplayName("Get habit by id")
    public void shouldReturnHabitWithId() {
        when(habitFacade.getHabitById(1L)).thenReturn(HABIT_DTO);

        MockMvcResponse response = RestAssuredMockMvc.get(BASE_PATH + "/1");
        verify(habitFacade).getHabitById(1L);
        response.then().status(HttpStatus.OK);

        HabitDto habitDto = response.as(HabitDto.class);
        assertNotNull(habitDto);
        assertEquals(HABIT_DTO.getId(), habitDto.getId());
        assertEquals(HABIT_DTO.getName(), habitDto.getName());
        assertEquals(HABIT_DTO.getDescription(), habitDto.getDescription());
    }

    @Test
    @DisplayName("Get habits by category")
    public void whenGetAllHabitsByCategoryThenReturnHabitsMatchingCategory() {
        when(habitFacade.getAllHabitsByCategory(CATEGORY)).thenReturn(HABIT_DTOS_WITH_CATEGORY);

        MockMvcResponse response = RestAssuredMockMvc.given()
                .queryParam("category", CATEGORY)
                .get(BASE_PATH);
        verify(habitFacade).getAllHabitsByCategory(CATEGORY);
        response.then().status(HttpStatus.OK);

        List<HabitDto> habitDtos = response.jsonPath().getList("", HabitDto.class);
        assertNotNull(habitDtos);
        assertTrue(habitDtos.stream().allMatch(habit -> CATEGORY.equals(habit.getCategory())));
        assertEquals(HABIT_DTOS_WITH_CATEGORY.size(), habitDtos.size());
        assertTrue(ListUtils.isEqualList(habitDtos, HABIT_DTOS_WITH_CATEGORY));
    }

    @Test
    @DisplayName("Create new habit")
    public void whenCreateNewHabitThenReturnHabitWithId() {
        CreateHabitRequest createHabitRequest = new CreateHabitRequest("Habit name1", "Habit description1", "Main category1", 3, TargetPeriod.DAILY, 1);
        when(habitFacade.createHabit(createHabitRequest)).thenReturn(HABIT_DTO);

        MockMvcResponse response = RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(createHabitRequest)
                .post(BASE_PATH);
        verify(habitFacade).createHabit(createHabitRequest);
        response.then().status(HttpStatus.OK);

        HabitDto habitDto = response.as(HabitDto.class);
        assertNotNull(habitDto);
        assertEquals(1L, habitDto.getId());
        assertEquals(createHabitRequest.getName(), habitDto.getName());
        assertEquals(createHabitRequest.getCategory(), habitDto.getCategory());
    }

    @Test
    @DisplayName("Update an existing habit by id")
    public void whenUpdateHabitThenReturnUpdatedHabit() {
        UpdateHabitRequest updateHabitRequest = new UpdateHabitRequest("Updated Habit name1", "Updated Habit description1");
        when(habitFacade.updateHabit(HABIT_DTO.getId(), updateHabitRequest)).thenReturn(UPDATED_HABIT_DTO);

        MockMvcResponse response = RestAssuredMockMvc.given()
                .queryParam("id", HABIT_DTO.getId())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(updateHabitRequest)
                .put(BASE_PATH + "/1");
        verify(habitFacade).updateHabit(HABIT_DTO.getId(), updateHabitRequest);
        response.then().status(HttpStatus.OK);

        HabitDto updatedHabitDto = response.as(HabitDto.class);
        assertNotNull(updatedHabitDto);
        assertEquals(HABIT_DTO.getId(), updatedHabitDto.getId());
        assertNotEquals(HABIT_DTO.getName(), updatedHabitDto.getName());
        assertNotEquals(HABIT_DTO.getDescription(), updatedHabitDto.getDescription());
    }
}
