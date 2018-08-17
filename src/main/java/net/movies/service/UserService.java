package net.movies.service;

import net.movies.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void register(User user,MultipartFile file);

    boolean existsByUsername(String username);

    boolean existsById(int id);

    void deleteById(int id);
}
