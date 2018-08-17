package net.movies.controller;

import net.movies.page.Pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @GetMapping("/login")
    public String login(){
        LOGGER.debug("redirect to '{}' page ");
        return LOGIN;
    }
}
