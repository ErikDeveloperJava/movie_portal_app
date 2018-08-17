package net.movies.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@ToString(exclude = "movies")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 4,max = 255,message = "in field username invalid data")
    private String username;

    @Length(min = 4,max = 255,message = "in field password invalid data")
    private String password;

    @Length(min = 4,max = 255,message = "in field name invalid data")
    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String image;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "bookmark",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies;
}
