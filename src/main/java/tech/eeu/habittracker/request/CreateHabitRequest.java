package tech.eeu.habittracker.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.eeu.habittracker.model.TargetPeriod;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitRequest {

    @NotBlank(message = "A name is required to create a habit!")
    private String name;

    @NotBlank(message = "A description is required to create a habit!")
    private String description;

    @NotNull(message = "The category can not be null, it must be empty or contain any characters!")
    private String category;

    @Min(value = 0, message = "The target can not be lower than 0!")
    @NotNull(message = "The target value can not be null")
    private Integer target;

    @NotNull(message = "The target period can not be null or empty!")
    private TargetPeriod targetPeriod;

    @NotNull(message = "The target progress can not be null, it must be atleast 0 or higher!")
    @Min(value = 0, message = "The target progress can not be lower than 0!")
    private Integer targetProgress;

    private Instant startDate;

    private Instant endDate;

}
