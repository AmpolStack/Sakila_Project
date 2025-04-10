package com.sakila.sakila_project.infrastructure.adapters.output.repositories.sakila;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sakila.sakila_project.domain.model.sakila.Film;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
}
