package com.sakila.sakila_project.domain.ports.output.repositories.sakila;

import com.sakila.sakila_project.domain.model.sakila.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
