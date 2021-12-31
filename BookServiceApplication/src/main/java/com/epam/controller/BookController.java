package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<List<Book>> getAllBooks() throws NoBooksException{
		return new ResponseEntity<>(bookService.fetchAllBooks(), HttpStatus.OK);
	}
	@GetMapping("/{book_id}")
	public ResponseEntity<Book> getBook(@PathVariable int book_id) throws BookNotFoundException{
		Book book = null;
		HttpStatus statusCode = null;
		if(bookService.getBook(book_id)!=null) {
			book = bookService.getBook(book_id);
			statusCode = HttpStatus.OK;
		}
		return new ResponseEntity<>(book,statusCode);
	}

	@PostMapping
	public ResponseEntity<String> addBook(@RequestBody BookDto bookDto) throws BookAlreadyExistsException{
		String status = "";
		HttpStatus statusCode = null;
		if (bookService.addBook(bookDto)) {
			status = "Book Added Successfully";
			statusCode = HttpStatus.ACCEPTED;
		} else {
			status = "Book Addition Unsuccessful";
			statusCode = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status, statusCode);
	}
	
	@DeleteMapping("/{book_id}")
	public ResponseEntity<String> deleteBook(@PathVariable int book_id) throws BookNotFoundException{
		String status = "";
		HttpStatus statusCode = null;
		if (bookService.deleteBook(book_id)) {
			status = "Book Deleted Successfully";
			statusCode = HttpStatus.ACCEPTED;
		} else {
			status = "Book Deletion Unsuccessful";
			statusCode = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status, statusCode);
	}

}
