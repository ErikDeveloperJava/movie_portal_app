package net.movies.controller;

import net.movies.model.User;
import net.movies.model.UserRole;
import net.movies.page.Pages;
import net.movies.service.PageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class MainController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private PageService pageService;

    @GetMapping("/")
    public String main(@RequestAttribute("user")User user,
                       Pageable pageable, Model model){
        if(user.getRole().equals(UserRole.ADMIN)){
            LOGGER.debug("authorization admin redirect to '/admin' url");
            return "redirect:/admin";
        }
        model.addAttribute("page",pageService.indexPage(user,pageable));
        model.addAttribute("pages",pageable.getPageNumber());
        LOGGER.debug("redirect to '{}' , {} model : {}",INDEX,"\n",model);
        return INDEX;
    }
}
