package net.movies.controller;

import net.movies.model.Comment;
import net.movies.model.Movie;
import net.movies.model.User;
import net.movies.repository.CommentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/add")
    public String add(@RequestParam("movieId")int movieId,
                      @RequestParam("comment")String commentText,
                      @RequestAttribute("user")User user){
        LOGGER.debug("parameters (movieId : {},comment : {} )",movieId,commentText);
        if(commentText.length() >= 2){
            Comment comment = Comment
                    .builder()
                    .addedDate(new Date())
                    .comment(commentText)
                    .movie(Movie.builder().id(movieId).build())
                    .user(user)
                    .build();
            commentRepository.save(comment);
            LOGGER.debug("comment saved");
        }
        LOGGER.debug("redirect to '/movie/{}' url",movieId);
        return "redirect:/movie/" + movieId;
    }
}
