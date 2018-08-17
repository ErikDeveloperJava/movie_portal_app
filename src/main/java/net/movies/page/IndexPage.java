package net.movies.page;

import lombok.Builder;
import lombok.Data;
import net.movies.model.Movie;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class IndexPage {

    private GenericPage genericPage;

    private List<Movie> topMovies;

    private SearchMoviesPage moviesPage;
}
