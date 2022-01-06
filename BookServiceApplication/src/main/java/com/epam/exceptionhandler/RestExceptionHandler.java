package com.epam.exceptionhandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.exception.BookAlreadyExistsException;
import com.epam.exception.BookNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {
	String bookService = "bookService";
	String books = "books";
	String timestamp = "timestamp";
	String error = "error";
	String status = "status";

	@ExceptionHandler(value = BookNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleBookNotFoundException(BookNotFoundException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(bookService, books);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.NOT_FOUND.name());
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = BookAlreadyExistsException.class)
	public ResponseEntity<Map<String, String>> handleBookAlreadyExistsException(BookAlreadyExistsException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(bookService, books);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.NOT_FOUND.name());
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
}
