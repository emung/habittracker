package tech.eeu.habittracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tech.eeu.habittracker.model.TargetPeriod;

@Data
@AllArgsConstructor
public class HabitDto {

    private Long id;

    private String name;

    private String description;

    private String category;

    private Integer target;

    private TargetPeriod targetPeriod;

    private Integer targetProgress;

}
