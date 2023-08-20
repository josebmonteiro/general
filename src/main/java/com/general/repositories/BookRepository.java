package com.general.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.general.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
