package com.senla.exceptions.handler;

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
        return new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchPointcutException(@NotNull NoHandlerFoundException e) {
        return new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUsernameNotFoundException(@NotNull UsernameNotFoundException e) {
        return new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(AccessDeniedException e) {
        return new ErrorResponse("Access denied!", LocalDateTime.now(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(@NotNull MethodArgumentTypeMismatchException e){
        return new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
    }
}
