package com.epam.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.epam.entity.Book;

public interface BookRepository extends CrudRepository<Book,Integer> {
	
	public Optional<Book> findByName(String name);

}
