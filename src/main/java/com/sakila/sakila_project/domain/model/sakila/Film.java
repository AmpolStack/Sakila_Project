package com.sakila.sakila_project.domain.model.sakila;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private int id;
    private String title;
    private String description;
    private int release_year;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;
    // private int language_id;
    private int rental_duration;
    private float rental_rate;
    private int length;
    private double replacement_cost;
    private String rating;
    private String special_features;
    private Date last_update;
    @OneToMany(mappedBy = "film")
    private Set<Inventory> inventory;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "films")
    private Set<Actor> actors;
}
