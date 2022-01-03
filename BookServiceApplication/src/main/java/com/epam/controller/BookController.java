package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.BookDto;
import com.epam.entity.Book;
import com.epam.exception.BookAlreadyExistsException;
import com.epam.exception.BookNotFoundException;
import com.epam.exception.NoBooksException;
import com.epam.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() throws NoBooksException {
		return new ResponseEntity<>(bookService.fetchAllBooks(), HttpStatus.OK);
	}

	@GetMapping("/{book_id}")
	public ResponseEntity<Book> getBook(@PathVariable("book_id") int bookId) throws BookNotFoundException {
		return new ResponseEntity<>(bookService.getBook(bookId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) throws BookAlreadyExistsException {
		return new ResponseEntity<>(bookService.addBook(bookDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/{book_id}")
	public ResponseEntity<String> deleteBook(@PathVariable("book_id") int bookId) throws BookNotFoundException {
		return new ResponseEntity<>(bookService.deleteBook(bookId), HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{book_id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable("book_id") int bookId, @RequestBody BookDto bookDto)
			throws BookNotFoundException {
		return new ResponseEntity<>(bookService.updateBook(bookId, bookDto), HttpStatus.ACCEPTED);
	}

}
