package tech.eeu.habittracker.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.eeu.habittracker.model.TargetPeriod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetHabitTargetRequest {

    @Min(value = 0, message = "The target can not be lower than 0!")
    @NotNull(message = "The target value can not be null")
    private Integer target;

    @NotNull(message = "The target period can not be null or empty!")
    private TargetPeriod targetPeriod;

}
