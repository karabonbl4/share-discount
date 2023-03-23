package com.senla.exceptions.handler;

import com.senla.exceptions.CardHasNotOwnerException;
import com.senla.exceptions.DataNeedToConfirmException;
import com.senla.exceptions.MissingRequiredDataException;
import com.senla.exceptions.ObjectAlreadyExistException;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.exceptions.ObjectBusyException;
import com.senla.exceptions.ObjectExpirationException;
import com.senla.exceptions.SortingNotExistException;
import com.senla.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class DefaultAdvise {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(EntityNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleResponseStatusException(ResponseStatusException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchPointcutException(NoHandlerFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(AccessDeniedException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.FORBIDDEN);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(MethodArgumentTypeMismatchException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleMethodNotAllowedException(HttpRequestMethodNotSupportedException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.METHOD_NOT_ALLOWED);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(ObjectAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistException(ObjectAlreadyExistException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(ObjectExpirationException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ErrorResponse handleObjectExpirationException(ObjectExpirationException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.GONE);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(SortingNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleSortingIsNotException(SortingNotExistException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(MissingRequiredDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingRequiredDataException(MissingRequiredDataException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(DataNeedToConfirmException.class)
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public ErrorResponse handleDataNeedToConfirmException(DataNeedToConfirmException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(ObjectBusyException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ErrorResponse handleObjectBusyException(ObjectBusyException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.GONE);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(CardHasNotOwnerException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleCardNotHaveOwnerException(CardHasNotOwnerException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.NO_CONTENT);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidParameterException(InvalidParameterException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getLocalizedMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalStateException(IllegalStateException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(":").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(NullPointerException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error(e.getClass().getSimpleName().concat(": ").concat(String.valueOf(errorResponse)));
        return errorResponse;
    }
}
