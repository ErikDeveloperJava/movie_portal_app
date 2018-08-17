package net.movies.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@ToString(exclude = "movies")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "ru_name")
    private String ruName;

    @Column(name = "arm_name")
    private String armName;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;
}
