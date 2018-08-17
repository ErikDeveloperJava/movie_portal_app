package net.movies.repository;

import net.movies.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

    @Query("select m from Movie m order by m.registerDate desc")
    List<Movie> findAllByRegisterDateDes(Pageable pageable);

    @Query("select m from Movie m join m.genres g on g.id=:genreId order by m.registerDate desc")
    List<Movie> findAllByGenreId(@Param("genreId")int genreId,Pageable pageable);

    @Query("select count(*) from Movie m join m.genres g on g.id=:genreId")
    int countByGenreId(@Param("genreId")int genreId);

    @Query("select m from Movie m join m.users u on u.id = :userId order by m.registerDate desc")
    List<Movie> findAllByUserId(@Param("userId")int userId,Pageable pageable);

    @Query("select m from Movie m order by size(m.users) desc")
    List<Movie> findAllByBookmark(Pageable pageable);

    @Query("select count(*) from Movie m join m.users u where u.id=:userId")
    int countByUserId(@Param("userId")int userId);

    List<Movie> findAllByTitleContainsAndIdIsNotIn(String title,int id,Pageable pageable);

    List<Movie> findAllByTitleContainsOrderByRegisterDateDesc(String tile,Pageable pageable);

    int countByTitleContains(String title);
}