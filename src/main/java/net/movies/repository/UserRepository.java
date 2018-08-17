package net.movies.repository;

import net.movies.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select u from User u order by u.id desc ")
    List<User> findAllByIdDesc(Pageable pageable);

    @Query("select count(*) from User u join u.movies m on u.id = :userId and m.id=:movieId")
    int countByBookmark(@Param("userId")int userId,@Param("movieId")int movieId);
}
