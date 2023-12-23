package tech.eeu.habittracker.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHabitRequest {

    @NotBlank(message = "A name is required to update a habit!")
    private String name;

    @NotBlank(message = "A description is required to update a habit!")
    private String description;

    @NotBlank(message = "A category is required to update a habit!")
    private String category;

    private Instant startDate;

    private Instant endDate;

}
