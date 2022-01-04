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
import com.epam.exception.NoBooksException;
import com.epam.repo.BookRepository;

@Service
public class BookService {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	ModelMapper mapper;
	String bookNotFound = "Book Not Found";

	public List<Book> fetchAllBooks() throws NoBooksException {
		List<Book> books = (List<Book>) bookRepository.findAll();
		if (books.isEmpty()) {
			throw new NoBooksException("No Books");
		}
		return books;
	}

	public Book getBook(int id) throws BookNotFoundException {
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(bookNotFound));
	}

	public BookDto addBook(BookDto bookDto) throws BookAlreadyExistsException {
		BookDto retrievedBookDto;
		Book book = mapper.map(bookDto, Book.class);
		Optional<Book> retrievedBook = bookRepository.findByName(bookDto.getName());
		if (retrievedBook.isEmpty()) {
			bookRepository.save(book);
			retrievedBookDto = mapper.map(book, BookDto.class);
		} else {
			throw new BookAlreadyExistsException("Book Already Exists");
		}
		return retrievedBookDto;
	}

	public String deleteBook(int id) throws BookNotFoundException {
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(bookNotFound));
		bookRepository.delete(book);
		return "Book Deleted Successfully";
	}

	public BookDto updateBook(int id, BookDto bookDto) throws BookNotFoundException {
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(bookNotFound));
		book.setName(bookDto.getName());
		book.setAuthor(bookDto.getAuthor());
		book.setPublisher(bookDto.getPublisher());
		bookRepository.save(book);
		return mapper.map(book, BookDto.class);
	}

}
