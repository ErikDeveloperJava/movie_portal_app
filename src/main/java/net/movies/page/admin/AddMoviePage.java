package net.movies.page.admin;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.movies.model.Country;
import net.movies.model.Genre;

import java.util.List;

@Data
@Builder
public class AddMoviePage {

    private List<Country> countries;

    private List<Genre> genres;
}
