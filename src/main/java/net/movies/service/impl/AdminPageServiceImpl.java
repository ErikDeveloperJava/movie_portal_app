package net.movies.service.impl;

import net.movies.page.admin.AddMoviePage;
import net.movies.page.admin.AdminMainPage;
import net.movies.repository.CountryRepository;
import net.movies.repository.GenreRepository;
import net.movies.repository.UserRepository;
import net.movies.service.AdminPageService;
import net.movies.util.PageServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminPageServiceImpl implements AdminPageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PageServiceUtil pageServiceUtil;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public AdminMainPage mainPage(Pageable pageable) {
        return AdminMainPage
                .builder()
                .users(userRepository.findAllByIdDesc(pageable))
                .arrayPage(pageServiceUtil.calculatePageCounts(userRepository.count()))
                .build();
    }

    @Override
    public AddMoviePage addMoviePage() {
        return AddMoviePage
                .builder()
                .countries(countryRepository.findAll())
                .genres(genreRepository.findAll())
                .build();
    }
}
