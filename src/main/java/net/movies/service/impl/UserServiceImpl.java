package net.movies.service.impl;

import net.movies.model.User;
import net.movies.model.UserRole;
import net.movies.repository.UserRepository;
import net.movies.service.UserService;
import net.movies.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageUtil imageUtil;

    @Override
    public void register(User user, MultipartFile file) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        user = userRepository.save(user);
        String img = user.getId() + new Random().nextInt(20000) + user.getUsername() + ".jpg";
        imageUtil.save("user",img,file);
        user.setImage(img);
        LOGGER.info("user saved");
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
        LOGGER.debug("user deleted");
    }
}
