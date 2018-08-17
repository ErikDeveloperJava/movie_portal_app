package net.movies.page;

import lombok.Builder;
import lombok.Data;
import net.movies.model.Comment;
import net.movies.model.Movie;

import java.util.List;

@Data
@Builder
public class MoviePage {

    private GenericPage genericPage;

    private Movie movie;

    private List<Movie> movies;

    private List<Comment> comments;

    private int isAddedToBookmark;
}
