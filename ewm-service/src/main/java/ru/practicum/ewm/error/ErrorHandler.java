package ru.practicum.ewm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleUserAlreadyExistException(final DataIntegrityException e) {
        return ApiErrorResponse.builder()
                .message(e.getMessage())
                .reason("Integrity constraint has been violated.")
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleUserNotFoundException(final NotFoundException e) {
        return ApiErrorResponse.builder()
                .message(e.getMessage())
                .reason("The required object was not found.")
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleRequestValidationException(final RequestValidationException e) {
        return ApiErrorResponse.builder()
                .message(e.getMessage())
                .reason("Integrity constraint has been violated.")
                .status(HttpStatus.CONFLICT.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidationException(final ValidationException e) {
        return ApiErrorResponse.builder()
                .message(e.getMessage())
                .reason("Incorrect request")
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleServiceException(final ServiceException e) {
        return ApiErrorResponse.builder()
                .message(e.getMessage())
                .reason("Failed to get statistics from Statistics Client.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleRuntimeException(final RuntimeException e) {
        return ApiErrorResponse.builder()
                .message(e.getMessage())
                .reason("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
