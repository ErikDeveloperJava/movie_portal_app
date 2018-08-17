package net.movies.service.impl;

import net.movies.model.Country;
import net.movies.model.Genre;
import net.movies.model.Movie;
import net.movies.repository.GenreRepository;
import net.movies.repository.MovieRepository;
import net.movies.service.MovieService;
import net.movies.util.ImageUtil;
import net.movies.util.VideoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private VideoUtil videoUtil;

    @Transactional(rollbackFor = Exception.class)
    public void save(Movie movie, int countryId, List<Integer> genres, MultipartFile... files) {
        List<Genre> genreList = new ArrayList<>();
        for (Integer id : genres) {
            genreList.add(genreRepository.findById(id).get());
        }
        movie.setGenres(genreList);
        movie.setCountry(Country.builder().id(countryId).build());
        movie.setProfileImage(" ");
        movie.setVideo(" ");
        movie = movieRepository.save(movie);
        Random random = new Random();
        movie.setProfileImage(movie.getId() + random.nextInt(20000) + ".jpg" );
        movie.setVideo(movie.getId() + random.nextInt(20000) + ".mp4" );
        movieRepository.flush();
        imageUtil.save("movies",movie.getProfileImage(),files[0]);
        try {
            videoUtil.save(movie.getVideo(),files[1]);
        }catch (RuntimeException e){
            imageUtil.delete("movies",movie.getProfileImage());
            throw e;
        }
        LOGGER.info("movie saved");
    }

    @Override
    public boolean existsById(int id) {
        return movieRepository.existsById(id);
    }
}
