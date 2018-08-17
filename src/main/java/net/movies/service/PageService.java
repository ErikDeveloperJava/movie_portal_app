package net.movies.service;

import net.movies.model.User;
import net.movies.page.GenericPage;
import net.movies.page.IndexPage;
import net.movies.page.MoviePage;
import org.springframework.data.domain.Pageable;

public interface PageService {

    IndexPage indexPage(User user, Pageable pageable);

    GenericPage genericPage(User user);

    MoviePage moviePage(int id,User user);

    IndexPage bookmarkPage(User user,Pageable pageable);
}
