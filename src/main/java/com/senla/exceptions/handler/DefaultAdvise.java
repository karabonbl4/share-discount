package com.senla.exceptions.handler;

import com.senla.exceptions.AlreadyExistException;
import com.senla.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class DefaultAdvise {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse handleNotFoundException(@NotNull EntityNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.OK);
        log.error(String.valueOf(errorResponse));
        return errorResponse;
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleResponseStatusException(@NotNull ResponseStatusException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(String.valueOf(errorResponse));
        return errorResponse;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchPointcutException(@NotNull NoHandlerFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error(String.valueOf(errorResponse));
        return errorResponse;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUsernameNotFoundException(@NotNull UsernameNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED);
        log.error(String.valueOf(errorResponse));
        return errorResponse;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(AccessDeniedException e) {
        ErrorResponse errorResponse = new ErrorResponse("Access denied!", LocalDateTime.now(), HttpStatus.FORBIDDEN);
        log.error(String.valueOf(errorResponse));
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(@NotNull MethodArgumentTypeMismatchException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(String.valueOf(errorResponse));
        return errorResponse;
    }
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistException(@NotNull AlreadyExistException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        log.error(String.valueOf(errorResponse));
        return errorResponse;
    }
}
