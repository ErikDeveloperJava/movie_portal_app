package net.movies.service;

import net.movies.model.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovieService {

    void save(Movie movie, int countryId, List<Integer> genres, MultipartFile...files);

    boolean existsById(int id);
}
