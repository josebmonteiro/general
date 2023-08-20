package com.general.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.general.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}