package com.portfolio.ledger.controller.advice;

import com.portfolio.ledger.controller.ReplyController;
import com.portfolio.ledger.exception.NotOwnerException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = {ReplyController.class})
@Log4j2
public class CustomRestAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleBindingException(BindException e) {
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        if(e.hasErrors()) {
            BindingResult bindingResult = e.getBindingResult();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleFKException(Exception e) {
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", "" + System.currentTimeMillis());
        errorMap.put("msg", "constraint fails");

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler({
            NoSuchElementException.class,
            EmptyResultDataAccessException.class
    })
    public ResponseEntity<Map<String, String>> handleNoSuchElement(Exception e) {
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", "" + System.currentTimeMillis());
        errorMap.put("msg", "No Such Element Exception");

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(NotOwnerException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleNotOwnerException(Exception e) {
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", "" + System.currentTimeMillis());
        errorMap.put("msg", "You are not owner");

        return ResponseEntity.badRequest().body(errorMap);
    }
}
