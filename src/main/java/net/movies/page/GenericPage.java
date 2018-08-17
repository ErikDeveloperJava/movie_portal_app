package net.movies.page;

import lombok.Builder;
import lombok.Data;
import net.movies.model.Genre;
import net.movies.model.Movie;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class GenericPage {

    private List<Genre> genres;

    private Map<String,List<Movie>> movieMap;

    private int bookmarks;
}