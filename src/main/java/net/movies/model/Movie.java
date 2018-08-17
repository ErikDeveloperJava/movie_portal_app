package net.movies.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@ToString(exclude = {"genres","users"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Length(min = 2,max = 255,message = "in field title invalid data")
    private String title;

    @Length(min = 10,message = "in field description invalid data")
    private String description;

    @Length(min = 4,max = 255,message = "in field actors invalid data")
    private String actors;

    @Length(min = 2,max = 255,message = "in field producer invalid data")
    private String producer;

    @NotNull(message = "premier date must be from past")
    @Past(message = "premier date must be from past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "premier_date")
    private Date premierDate;

    @Length(min = 2,max = 255,message = "in field language invalid data")
    private String language;

    @Range(min = 1970,max = 2018,message = "in field year invalid data")
    private int year;

    @Column(name = "profile_image")
    private String profileImage;

    private String video;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_date")
    private Date registerDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name = "movie_genre",joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @ManyToMany(mappedBy = "movies")
    private List<User> users;
}