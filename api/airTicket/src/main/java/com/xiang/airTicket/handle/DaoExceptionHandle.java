package com.xiang.airTicket.handle;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DaoExceptionHandle {
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<JsonErrorResult> associateDeleteExceptionHandler(final HttpServletRequest httpServletRequest, final Exception exception) {
        return new ResponseEntity<>(new JsonErrorResult(exception.getMessage()), HttpStatus.CONFLICT);
    }
}

class JsonErrorResult {
    @JsonProperty
    String errorMessage;

    JsonErrorResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
