package tech.eeu.habittracker.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHabitCategoryRequest {

    @NotNull(message = "The category can not be null, it must be empty or contain any characters!")
    private String category;

}
