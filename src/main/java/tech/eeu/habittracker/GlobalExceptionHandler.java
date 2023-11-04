package tech.eeu.habittracker;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.eeu.habittracker.exception.HabitNotFoundException;
import tech.eeu.habittracker.exception.HabitTargetProgressDecrementException;
import tech.eeu.habittracker.exception.HabitTargetProgressIncrementException;
import tech.eeu.habittracker.response.RestErrorResponse;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HabitNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public RestErrorResponse handleHabitNotFoundException(HabitNotFoundException habitNotFoundException, HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return RestErrorResponse.builder()
                .error(httpStatus.getReasonPhrase())
                .message(habitNotFoundException.getMessage())
                .status(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(HabitTargetProgressIncrementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestErrorResponse handleHabitTargetProgressIncrementException(HabitTargetProgressIncrementException habitTargetProgressIncrementException, HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return RestErrorResponse.builder()
                .error(httpStatus.getReasonPhrase())
                .message(habitTargetProgressIncrementException.getMessage())
                .status(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(HabitTargetProgressDecrementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestErrorResponse handleHabitTargetProgressDecrementException(HabitTargetProgressDecrementException habitTargetProgressDecrementException, HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return RestErrorResponse.builder()
                .error(httpStatus.getReasonPhrase())
                .message(habitTargetProgressDecrementException.getMessage())
                .status(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return RestErrorResponse.builder()
                .message("")
                .error(httpStatus.getReasonPhrase())
                .errors(getErrors(methodArgumentNotValidException))
                .status(httpStatus.value())
                .timestamp(LocalDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .build();
    }

    private static List<String> getErrors(MethodArgumentNotValidException methodArgumentNotValidException) {
        return methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
    }


}
