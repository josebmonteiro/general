package com.general.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.general.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
