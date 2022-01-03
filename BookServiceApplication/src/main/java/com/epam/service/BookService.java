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

	public List<Book> fetchAllBooks() throws NoBooksException {
		List<Book> books = (List<Book>) bookRepository.findAll();
		if (books.isEmpty()) {
			throw new NoBooksException("No Books");
		}
		return books;
	}

	public Book getBook(int id) throws BookNotFoundException {
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found"));
	}

	public BookDto addBook(BookDto bookDto) throws BookAlreadyExistsException {
//		BookDto bookDto1 = null;
//		Book book = mapper.map(bookDto, Book.class);
//		Optional<Book> optionalBook = bookRepository.findByName(bookDto.getName());
//		if (!optionalBook.isPresent()) {
//			bookRepository.save(book);
//			bookDto1 = mapper.map(book, BookDto.class);
//		} else {
//			throw new BookAlreadyExistsException("Book Already Exists");
//		}
//		return bookDto1;
		BookDto bookDto1 = null;
		Book book = mapper.map(bookDto, Book.class);
		Optional<Book> book1= bookRepository.findByName(bookDto.getName());
		if(book1.isEmpty()) {
			bookRepository.save(book);
			bookDto1 = mapper.map(book, BookDto.class);
		}
		else {
			throw new BookAlreadyExistsException("Book Already Exists");
		}
		return bookDto1;
	}

	public String deleteBook(int id) throws BookNotFoundException {
		String status = "";
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found"));
		if (book != null) {
			bookRepository.delete(book);
			status = "Book Deleted Successfully";
		}
		return status;
	}

	public BookDto updateBook(int id, BookDto bookDto) throws BookNotFoundException {
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book Not Found"));
		book.setName(bookDto.getName());
		book.setAuthor(bookDto.getAuthor());
		book.setPublisher(bookDto.getPublisher());
		bookRepository.save(book);
		BookDto bookDto1  = mapper.map(book, BookDto.class);
		return bookDto1;
	}

}
