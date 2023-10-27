package tech.eeu.habittracker.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecrementProgressRequest {

    @PositiveOrZero(message = "The decrement value can not be lower than 0!")
    @NotNull(message = "The decrement value can not be null")
    @Setter(AccessLevel.PRIVATE)
    private Integer decrementBy;

}
