package tech.eeu.habittracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.eeu.habittracker.model.TargetPeriod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitDto {

    private Long id;

    private String name;

    private String description;

    private String category;

    private Integer target;

    private TargetPeriod targetPeriod;

    private Integer targetProgress;

}
