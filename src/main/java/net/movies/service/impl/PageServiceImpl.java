package net.movies.service.impl;

import net.movies.model.Genre;
import net.movies.model.Movie;
import net.movies.model.User;
import net.movies.model.UserRole;
import net.movies.page.GenericPage;
import net.movies.page.IndexPage;
import net.movies.page.MoviePage;
import net.movies.page.SearchMoviesPage;
import net.movies.repository.CommentRepository;
import net.movies.repository.GenreRepository;
import net.movies.repository.MovieRepository;
import net.movies.repository.UserRepository;
import net.movies.service.PageService;
import net.movies.util.PageServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PageServiceImpl implements PageService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PageServiceUtil pageServiceUtil;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public IndexPage indexPage(User user, Pageable pageable) {
        SearchMoviesPage moviesPage = SearchMoviesPage
                .builder()
                .movies(movieRepository.findAllByRegisterDateDes(pageable))
                .arrayPage(pageServiceUtil.calculatePageCounts(movieRepository.count()))
                .build();
        return IndexPage
                .builder()
                .topMovies(movieRepository.findAllByBookmark(PageRequest.of(0,4)))
                .genericPage(genericPage(user))
                .moviesPage(moviesPage)
                .build();
    }

    @Override
    public GenericPage genericPage(User user){
        int bookmarks = 0;
        if(user.getRole() == UserRole.USER){
            bookmarks = movieRepository.countByUserId(user.getId());
        }
        List<Genre> genres = genreRepository.findAll();
        return GenericPage
                .builder()
                .movieMap(pageServiceUtil.getMovieMap(genres,movieRepository))
                .genres(genres)
                .bookmarks(bookmarks)
                .build();
    }

    @Override
    public MoviePage moviePage(int id,User user) {
        Movie movie = movieRepository.findById(id).get();
        int isAddedToBookmark = 0;
        if(user.getRole() == UserRole.USER){
            isAddedToBookmark = userRepository.countByBookmark(user.getId(),id);
        }
        return MoviePage
                .builder()
                .genericPage(genericPage(user))
                .movie(movie)
                .movies(movieRepository.findAllByTitleContainsAndIdIsNotIn(String.valueOf(movie.getTitle().charAt(0)),id,PageRequest.of(0,3)))
                .comments(commentRepository.findAllByMovieId(id))
                .isAddedToBookmark(isAddedToBookmark)
                .build();
    }

    @Override
    public IndexPage bookmarkPage(User user, Pageable pageable) {
        SearchMoviesPage moviesPage = SearchMoviesPage
                .builder()
                .movies(movieRepository.findAllByUserId(user.getId(),pageable))
                .arrayPage(pageServiceUtil.calculatePageCounts(movieRepository.countByUserId(user.getId())))
                .build();
        return IndexPage
                .builder()
                .genericPage(genericPage(user))
                .moviesPage(moviesPage)
                .topMovies(movieRepository.findAllByBookmark(PageRequest.of(0,4)))
                .build();
    }
}
