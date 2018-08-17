package net.movies.controller.admin;

import net.movies.model.Movie;
import net.movies.page.Pages;
import net.movies.service.AdminPageService;
import net.movies.service.MovieService;
import net.movies.util.ImageUtil;
import net.movies.util.VideoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/movie/add")
public class AddMovieController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AdminPageService pageService;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private VideoUtil videoUtil;

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String add(Model model){
        model.addAttribute("movie",Movie.builder().build());
        LOGGER.debug("redirect to '{}' page , model : {}",ADMIN_ADD_MOVIE_PAGE,model);
        return ADMIN_ADD_MOVIE_PAGE;
    }

    @PostMapping("/1")
    public String addPage1(@Valid Movie movie, BindingResult result,
                           HttpServletRequest request,Model model){
        LOGGER.debug("parameters ( {},error counts : {} )",movie,result.getFieldErrorCount());
        if(result.hasErrors()){
            LOGGER.debug("redirect to '{}' page",ADMIN_ADD_MOVIE_PAGE);
            return ADMIN_ADD_MOVIE_PAGE;
        }
        HttpSession session = request.getSession();
        session.setAttribute("movie",movie);
        LOGGER.debug("movie added to session attribute");
        model.addAttribute("page",pageService.addMoviePage());
        LOGGER.debug("redirect to '{}' page,model : {}",ADMIN_ADD_MOVIE_PAGE_2,model);
        return ADMIN_ADD_MOVIE_PAGE_2;
    }

    @PostMapping("/2")
    public String addPage2(@RequestParam("countryId")int id,
                           @RequestParam(value = "genre",required = false)List<Integer> genres,
                           @RequestParam("image")MultipartFile image,@RequestParam("video")MultipartFile video,
                           HttpServletRequest request,Model model){
        LOGGER.debug("parameters ( countryId : {},genres : {},\nimage isEmpty : {}, video isEmpty : {})",
                id,genres,image.isEmpty(),video.isEmpty());
        HttpSession session = request.getSession();
        Movie movie = (Movie) session.getAttribute("movie");
        if(movie == null){
            LOGGER.info("session attribute 'Movie' equals null,redirect to '{}' page",ADMIN_ADD_MOVIE_PAGE);
            return ADMIN_ADD_MOVIE_PAGE;
        }else {
            LOGGER.info("movie : {}",movie);
        }
        if(genres == null){
            model.addAttribute("page",pageService.addMoviePage());
            model.addAttribute("genreError","please select a genre");
            LOGGER.debug("genres empty ,redirect to '{}' page",ADMIN_ADD_MOVIE_PAGE_2);
            return ADMIN_ADD_MOVIE_PAGE_2;
        }
        if(image.isEmpty() || !imageUtil.checkImageFormat(image.getContentType())){
            model.addAttribute("imageError","invalid image file");
            model.addAttribute("page",pageService.addMoviePage());
            LOGGER.debug("invalid image file,redirect to '{}' page",ADMIN_ADD_MOVIE_PAGE_2);
            return ADMIN_ADD_MOVIE_PAGE_2;
        }
        if(video.isEmpty() || !videoUtil.checkVideoFormat(video.getContentType())){
            model.addAttribute("videoError","invalid video file");
            model.addAttribute("page",pageService.addMoviePage());
            LOGGER.debug("invalid video file,redirect to '{}' page",ADMIN_ADD_MOVIE_PAGE_2);
            return ADMIN_ADD_MOVIE_PAGE_2;
        }
        movie.setRegisterDate(new Date());
        movieService.save(movie,id,genres,image,video);
        LOGGER.debug("redirect to '/admin' url");
        return "redirect:/admin";
    }
}
