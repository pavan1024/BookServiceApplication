package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dto.BookDto;
import com.epam.entity.Book;
import com.epam.exception.BookAlreadyExistsException;
import com.epam.exception.BookNotFoundException;
import com.epam.repo.BookRepository;

@SpringBootTest
class BookServiceTest {

	@Mock
	BookRepository bookRepository;

	@Mock
	ModelMapper mapper;

	@InjectMocks
	BookService bookService;

	Book book;
	BookDto bookDto;
	List<Book> books;

	@BeforeEach
	void setUp() {
		book = new Book();
		book.setId(1);
		book.setName("book");
		book.setAuthor("bookauthor");
		book.setPublisher("bookpublisher");

		bookDto = new BookDto();
		bookDto.setId(2);
		bookDto.setName("newBook");
		bookDto.setAuthor("newBookAuthor");
		bookDto.setPublisher("newbookpublisher");

		books = new ArrayList<>();
		books.add(book);
	}

	@Test
	void addBookTest() {
		when(mapper.map(bookDto, Book.class)).thenReturn(book);
		when(mapper.map(book, BookDto.class)).thenReturn(bookDto);
		Optional<Book> optionalBook = Optional.empty();
		when(bookRepository.findByName(bookDto.getName())).thenReturn(optionalBook);
		assertEquals(bookDto, bookService.addBook(bookDto));
	}

	@Test
	void addBookErrorTest() {
		Optional<Book> optionalBook = Optional.ofNullable(book);
		when(bookRepository.findByName(bookDto.getName())).thenReturn(optionalBook);
		Throwable exception = assertThrows(BookAlreadyExistsException.class, () -> bookService.addBook(bookDto));
		assertEquals("Book Already Exists", exception.getMessage());
	}

	@Test
	void deleteBookTest() {
		Optional<Book> optionalBook = Optional.ofNullable(book);
		when(bookRepository.findById(2)).thenReturn(optionalBook);
		assertEquals("Book Deleted Successfully", bookService.deleteBook(2));
	}

	@Test
	void deleteBookErrorTest() {
		Throwable exception = assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1));
		assertEquals("Book Not Found", exception.getMessage());
	}

	@Test
	void getBookTest() {
		Book book1 = new Book();
		book1.setId(2);
		book1.setName("newBook");
		book1.setAuthor("newBookAuthor");
		book1.setPublisher("newbookpublisher");
		Optional<Book> optionalBook = Optional.ofNullable(book1);
		when(bookRepository.findById(2)).thenReturn(optionalBook);
		assertEquals(book1, bookService.getBook(2));
	}

	@Test
	void getBookErrorTest() {
		Throwable exception = assertThrows(BookNotFoundException.class, () -> bookService.getBook(1));
		assertEquals("Book Not Found", exception.getMessage());
	}

	@Test
	void getAllBooksTest() {
		when(bookRepository.findAll()).thenReturn(books);
		assertEquals(books, bookService.fetchAllBooks());
	}

	@Test
	void updateBookTest() {
		when(mapper.map(book, BookDto.class)).thenReturn(bookDto);
		Optional<Book> optionalBook = Optional.ofNullable(book);
		when(bookRepository.findById(2)).thenReturn(optionalBook);
		assertEquals(bookDto, bookService.updateBook(2, bookDto));
	}

	@Test
	void updateBookErrorTest() {
		Throwable exception = assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1, bookDto));
		assertEquals("Book Not Found", exception.getMessage());
	}

}
