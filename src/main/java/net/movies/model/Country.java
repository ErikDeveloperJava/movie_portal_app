package net.movies.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "ru_name")
    private String ruName;

    @Column(name = "arm_name")
    private String armName;
}
