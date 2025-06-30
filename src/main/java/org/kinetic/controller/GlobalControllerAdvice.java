package org.kinetic.controller;

import lombok.extern.slf4j.Slf4j;
import org.kinetic.exception.ProcessNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ExceptionHandler(ProcessNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ProcessNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body("Internal error occurred.");
    }
}
