package net.movies.service.impl;

import net.movies.model.Movie;
import net.movies.model.User;
import net.movies.repository.MovieRepository;
import net.movies.repository.UserRepository;
import net.movies.service.BookmarkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public void add(int userId, int movieId) {
        Movie movie = movieRepository.findById(movieId).get();
        User user = userRepository.findById(userId).get();
        List<Movie> movies = user.getMovies();
        movies.add(movie);
        user.setMovies(movies);
        LOGGER.debug("bookmark added");
    }

    @Transactional
    public void delete(int userId, int movieId) {
        Movie movie = movieRepository.findById(movieId).get();
        User user = userRepository.findById(userId).get();
        List<Movie> movies = user.getMovies();
        movies.remove(movie);
        user.setMovies(movies);
        LOGGER.debug("bookmark deleted");
    }
}
