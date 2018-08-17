package net.movies.service.impl;

import net.movies.model.User;
import net.movies.page.IndexPage;
import net.movies.page.SearchMoviesPage;
import net.movies.repository.MovieRepository;
import net.movies.service.PageService;
import net.movies.service.SearchService;
import net.movies.util.PageServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PageService pageService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PageServiceUtil pageServiceUtil;

    @Override
    public IndexPage searchByGenre(User user, int genreId, Pageable pageable) {
        SearchMoviesPage searchMoviesPage = SearchMoviesPage
                .builder()
                .movies(movieRepository.findAllByGenreId(genreId,pageable))
                .arrayPage(pageServiceUtil.calculatePageCounts(movieRepository.countByGenreId(genreId)))
                .build();
        return IndexPage
                .builder()
                .topMovies(movieRepository.findAllByBookmark(PageRequest.of(0,4)))
                .genericPage(pageService.genericPage(user))
                .moviesPage(searchMoviesPage)
                .build();
    }

    @Override
    public IndexPage searchByTitle(User user, String title, Pageable pageable) {
        SearchMoviesPage searchMoviesPage = SearchMoviesPage
                .builder()
                .movies(movieRepository.findAllByTitleContainsOrderByRegisterDateDesc(title,pageable))
                .arrayPage(pageServiceUtil.calculatePageCounts(movieRepository.countByTitleContains(title)))
                .build();
        return IndexPage
                .builder()
                .topMovies(movieRepository.findAllByBookmark(PageRequest.of(0,4)))
                .genericPage(pageService.genericPage(user))
                .moviesPage(searchMoviesPage)
                .build();
    }
}
