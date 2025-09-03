package com.example.neoappclientesapi.handler;

import com.example.neoappclientesapi.dto.APIResponse;
import com.example.neoappclientesapi.dto.ErrorDTO;
import com.example.neoappclientesapi.exception.DuplicatedResourceException;
import com.example.neoappclientesapi.exception.InvalidCredentialsException;
import com.example.neoappclientesapi.exception.ResourceNotFoundException;
import com.example.neoappclientesapi.util.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorDTO> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
                    errors.add(errorDTO);
                });

        return new ResponseEntity<>(ResponseUtils.buildErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ResponseUtils.buildSingleError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DuplicatedResourceException.class)
    public ResponseEntity<APIResponse<Void>> handleDuplicatedContent(DuplicatedResourceException ex) {
        return new ResponseEntity<>(ResponseUtils.buildSingleError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<APIResponse<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseEntity<>(ResponseUtils.buildSingleError(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}
