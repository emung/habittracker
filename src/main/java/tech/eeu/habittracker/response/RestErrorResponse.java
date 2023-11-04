package tech.eeu.habittracker.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestErrorResponse {

    private String message;

    private String error;

    @Getter()
    private List<String> errors;

    private String path;

    private Integer status;

    private LocalDateTime timestamp;

}
