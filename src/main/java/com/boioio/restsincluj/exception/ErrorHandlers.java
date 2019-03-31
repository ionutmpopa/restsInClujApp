package com.boioio.restsincluj.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandlers {

    private static Logger LOGGER = LoggerFactory.getLogger(ErrorHandlers.class);

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handle(RuntimeException e) {
        LOGGER.error("ups", e);

        ApplicationError applicationError = new ApplicationError();
        applicationError.setCode(HttpStatus.OK.value());
        applicationError.setMessage(e.getMessage());


        ModelAndView errorResponse = new ModelAndView("error");
        errorResponse.addObject("applicationError", applicationError);

        return errorResponse;
    }
}
