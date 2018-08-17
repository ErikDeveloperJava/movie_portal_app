package net.movies.controller;

import net.movies.model.Genre;
import net.movies.model.User;
import net.movies.page.Pages;
import net.movies.repository.GenreRepository;
import net.movies.service.SearchService;
import net.movies.util.ControllerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/movie")
public class SearchController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ControllerUtil controllerUtil;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/genre/{id}")
    public String searchByGenre(@PathVariable("id")String strId,
                                @RequestAttribute("user")User user,
                                Pageable pageable,Model model){
        LOGGER.debug("parameters ( genreId : {} )",strId);
        int genreId = controllerUtil.parseToInteger(strId);
        Optional<Genre > genre;
        if(genreId == -1 || !(genre = genreRepository.findById(genreId)).isPresent()){
            LOGGER.debug("invalid id ,redirect to '/' url");
            return "redirect:/";
        }
        model.addAttribute("page",searchService.searchByGenre(user,genreId,pageable));
        model.addAttribute("pages",pageable.getPageNumber());
        model.addAttribute("genre",genre.get());
        LOGGER.debug("redirect to '{}' page,model : {}",SEARCH_MOVIES,model);
        return SEARCH_MOVIES;
    }

    @PostMapping("/search")
    public String searchByTitle(@RequestParam("title")String title,
                                @RequestAttribute("user")User user,
                                Pageable pageable,Model model){
        LOGGER.debug("parameters ( title : {} )",title);
        model.addAttribute("page",searchService.searchByTitle(user,title,pageable));
        model.addAttribute("pages",pageable.getPageNumber());
        model.addAttribute("title",title);
        LOGGER.debug("redirect to '{}' page,model : {}",SEARCH_MOVIES,model);
        return SEARCH_MOVIES;
    }
}
