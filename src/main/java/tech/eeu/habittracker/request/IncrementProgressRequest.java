package tech.eeu.habittracker.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncrementProgressRequest {

    @PositiveOrZero(message = "The increment value can not be lower than 0!")
    @NotNull(message = "The increment value can not be null")
    @Setter(AccessLevel.PRIVATE)
    private Integer incrementBy;

}
