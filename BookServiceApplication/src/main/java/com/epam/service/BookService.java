package com.epam.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.BookDto;
import com.epam.entity.Book;
import com.epam.exception.BookAlreadyExistsException;
import com.epam.exception.BookNotFoundException;
import com.epam.repo.BookRepository;

@Service
public class BookService {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	ModelMapper mapper;
	String bookNotFound = "Book Not Found";

	public List<Book> fetchAllBooks() {
		return (List<Book>) bookRepository.findAll();
	}

	public Book getBook(int id) throws BookNotFoundException {
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(bookNotFound));
	}

	public BookDto addBook(BookDto bookDto) throws BookAlreadyExistsException {
		Optional<Book> retrievedBook = bookRepository.findByName(bookDto.getName());
		if (retrievedBook.isPresent()) {
			throw new BookAlreadyExistsException("Book Already Exists");
		}
		Book book = mapper.map(bookDto, Book.class);
		bookRepository.save(book);
		bookDto.setId(book.getId());
		return bookDto;
	}

	public String deleteBook(int id) throws BookNotFoundException {
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(bookNotFound));
		bookRepository.delete(book);
		return "Book Deleted Successfully";
	}

	public BookDto updateBook(int id, BookDto bookDto) throws BookNotFoundException {
		Book retrievedBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(bookNotFound));
		bookDto.setId(id);
		mapper.map(bookDto, Book.class);
		bookRepository.save(retrievedBook);
		return bookDto;
	}

}
