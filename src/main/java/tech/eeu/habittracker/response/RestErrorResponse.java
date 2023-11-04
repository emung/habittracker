package tech.eeu.habittracker.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestErrorResponse {

    private String message;

    private String error;

    private List<String> errors;

    private String path;

    private Integer status;

    private LocalDateTime timestamp;

}
