package tech.eeu.habittracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HabitDto {

    private Long id;

    private String name;

    private String description;

    private String category;

}
