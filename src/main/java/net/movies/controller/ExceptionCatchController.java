package net.movies.controller;

import net.movies.page.Pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionCatchController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(Exception.class)
    public String exceptionCatch(Exception e, HttpServletResponse response){
        try {
            response.sendError(500);
        } catch (IOException e1) {
            LOGGER.error("response.sendError(500) exception",e1);
        }
        LOGGER.error(e.getMessage(),e);
        return ERROR_500;
    }
}
