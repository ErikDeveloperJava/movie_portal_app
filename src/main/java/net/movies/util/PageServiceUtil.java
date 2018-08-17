package net.movies.util;

import net.movies.model.Genre;
import net.movies.model.Movie;
import net.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageServiceUtil {

    @Autowired
    private Environment environment;

    private static final int SIZE = 3;

    public int[]calculatePageCounts(long count) {
        int defaultPageSize = Integer.parseInt(environment.getProperty("spring.data.web.pageable.default-page-size"));
        System.out.println(defaultPageSize);
        int length;
        if (count == 0 || count < defaultPageSize) {
            length = 1;
        } else if (count % defaultPageSize != 0) {
            length = (int) count / defaultPageSize + 1;
        } else {
            length = (int) count / defaultPageSize;
        }
        return new int[length];
    }

    public Map<String,List<Movie>> getMovieMap(List<Genre> genres, MovieRepository movieRepository){
        Map<String,List<Movie>> movieMap = new LinkedHashMap<>();
        int index = 1;
        for (Genre genre : genres) {
            movieMap.put("tab" + index++,movieRepository.findAllByGenreId(genre.getId(),PageRequest.of(0,SIZE)));
        }
        return movieMap;
    }
}
