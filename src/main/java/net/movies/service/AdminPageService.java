package net.movies.service;

import net.movies.page.admin.AddMoviePage;
import net.movies.page.admin.AdminMainPage;
import org.springframework.data.domain.Pageable;


public interface AdminPageService {

    AdminMainPage mainPage(Pageable pageable);

    AddMoviePage addMoviePage();
}
