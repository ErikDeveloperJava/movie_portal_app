package net.movies.service;

import net.movies.model.User;
import net.movies.page.IndexPage;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    IndexPage searchByGenre(User user, int genreId, Pageable pageable);

    IndexPage searchByTitle(User user, String title, Pageable pageable);
}
