package com.epam.service;

import java.util.ArrayList;
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
	
	public List<Book> fetchAllBooks() throws NoBooksException{
		List<Book> books = new ArrayList<>();
		books=(List<Book>) bookRepository.findAll();
		if(books.isEmpty()) {
			throw new NoBooksException("No Books");
		}
		return books;
	}
	
	public Book getBook(int id) throws BookNotFoundException{
		Book retrivedBook = null;
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()) {
			retrivedBook = book.get();
		}
		else {
			throw new BookNotFoundException("Book Not Found");
		}
		return retrivedBook;
	}
	
	public boolean addBook(BookDto bookDto) throws BookAlreadyExistsException {
		boolean status = false;
		Optional<Book> optionalBook = bookRepository.findByName(bookDto.getName());
		Book book = mapper.map(bookDto, Book.class);
		if(!optionalBook.isPresent()) {
			bookRepository.save(book);
			status = true;
		}
		else {
			throw new BookAlreadyExistsException("Book Already Exists");
		}
		return status;
	}
	
	public boolean deleteBook(int id) throws BookNotFoundException{
		boolean status = false;
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()) {
			bookRepository.delete(book.get());
			status = true;
		}
		else {
			throw new BookNotFoundException("Book Not Found");
		}
		return status;
	}
	
	public boolean updateBook(int id,BookDto bookDto) throws BookNotFoundException{
		boolean status = false;
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()) {
			book.get().setName(bookDto.getName());
			book.get().setAuthor(bookDto.getAuthor());
			book.get().setPublisher(bookDto.getPublisher());
			bookRepository.save(book.get());
			status = true;
		}
		else {
			throw new BookNotFoundException("Book Not Found");
		}
		return status;
	}
	

}
