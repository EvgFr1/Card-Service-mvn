package ru.cards.SpringCard.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String,String> handleIllegalArgumentException(IllegalArgumentException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return response;
    }

}
