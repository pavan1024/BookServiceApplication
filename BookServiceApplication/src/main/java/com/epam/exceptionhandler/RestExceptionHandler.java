package com.epam.exceptionhandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.exception.BookAlreadyExistsException;
import com.epam.exception.BookNotFoundException;
import com.epam.exception.NoBooksException;


@RestControllerAdvice
public class RestExceptionHandler {
	String bookService = "bookService";
	String books = "books";
	String timestamp = "timestamp";
	String error = "error";
	String status = "status";
	
	@ExceptionHandler(value = BookNotFoundException.class)
	public Map<String, String> handleBookNotFoundException(BookNotFoundException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(bookService, books);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.NOT_FOUND.name());
		return response;
	}

	@ExceptionHandler(value = BookAlreadyExistsException.class)
	public Map<String, String> handleBookAlreadyExistsException(BookAlreadyExistsException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(bookService, books);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.NOT_FOUND.name());
		return response;
	}
	
	@ExceptionHandler(value = NoBooksException.class)
	public Map<String, String> handleNoBooksException(NoBooksException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(bookService, books);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.NOT_FOUND.name());
		return response;
	}
}
