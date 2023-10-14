package tech.eeu.habittracker.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitRequest {

    @NotBlank(message = "A name is required to create a habit!")
    private String name;

    @NotBlank(message = "A description is required to create a habit!")
    private String description;

}
