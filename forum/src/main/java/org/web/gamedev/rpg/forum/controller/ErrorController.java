package org.web.gamedev.rpg.forum.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ErrorController {

    @AllArgsConstructor
    private static class ErrorMessage {
        HttpStatus status;
        Date date;
        String message;
        String description;
    }

    private class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpEntity globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        //return message;
        return new HttpEntity(ex.getMessage());
    }

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public String error(@RequestParam(value = "message", required = false) String message) {
        //TODO
        return message;
    }
}