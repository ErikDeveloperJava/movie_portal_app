package net.movies.page;


import lombok.Builder;
import lombok.Data;
import net.movies.model.Movie;

import java.util.List;

@Data
@Builder
public class SearchMoviesPage {

    private List<Movie> movies;

    private int[] arrayPage;
}
