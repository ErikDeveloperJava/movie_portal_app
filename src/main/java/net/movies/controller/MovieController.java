package net.movies.controller;

import net.movies.model.User;
import net.movies.page.Pages;
import net.movies.service.MovieService;
import net.movies.service.PageService;
import net.movies.util.ControllerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie")
public class MovieController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MovieService movieService;

    @Autowired
    private PageService pageService;

    @Autowired
    private ControllerUtil controllerUtil;

    @GetMapping("/{id}")
    public String movie(@PathVariable("id")String strId,
                        @RequestAttribute("user")User user, Model model){
        LOGGER.debug("parameters ( id : {})",strId);
        int id = controllerUtil.parseToInteger(strId);
        if(id == -1 || !movieService.existsById(id)){
            LOGGER.debug("invalid id ,redirect to '/' url");
            return "redirect:/";
        }
        model.addAttribute("page",pageService.moviePage(id,user));
        LOGGER.debug("redirect to '{}' page , model : {}",MOVIE,model);
        return MOVIE;
    }

}
