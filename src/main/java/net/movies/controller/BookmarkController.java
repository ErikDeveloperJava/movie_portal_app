package net.movies.controller;

import net.movies.model.User;
import net.movies.page.Pages;
import net.movies.service.BookmarkService;
import net.movies.service.PageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private PageService pageService;

    @PostMapping("/add")
    public String add(@RequestParam("movieId")int id,
                      @RequestAttribute("user")User user){
        bookmarkService.add(user.getId(),id);
        LOGGER.debug("redirect to '/movie/{}' url",id);
        return "redirect:/movie/" + id;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("movieId")int id,
                      @RequestAttribute("user")User user){
        bookmarkService.delete(user.getId(),id);
        LOGGER.debug("redirect to '/movie/{}' url",id);
        return "redirect:/movie/" + id;
    }

    @GetMapping("/all")
    public String bookmarks(@RequestAttribute("user")User user,
                            Pageable pageable, Model model){
        model.addAttribute("page",pageService.bookmarkPage(user,pageable));
        model.addAttribute("pages",pageable.getPageNumber());
        LOGGER.debug("redirect to '{}' page , model : {}",BOOKMARK,model);
        return BOOKMARK;
    }
}
