package com.epam.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import com.epam.dto.BookDto;
import com.epam.entity.Book;
import com.epam.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	BookService bookService;
	
	ObjectMapper mapper;
	BookDto bookDto;
	
	@BeforeEach
	void setUp() {
		mapper = new ObjectMapper();
		bookDto =new BookDto();
		bookDto.setId(1);
		bookDto.setName("bookname");
		bookDto.setAuthor("bookauthor");
		bookDto.setPublisher("bookpublisher");
		
	}
	
	@Test
	void addBookTest() throws Exception {		
		when(bookService.addBook(any())).thenReturn(true);
		MvcResult result = mockMvc
				.perform(post("/books/").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(bookDto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertEquals("Book Added Successfully", response);
		
	}
	
	@Test
	void addBookErrorTest() throws Exception {
		when(bookService.addBook(any())).thenReturn(false);
		MvcResult result = mockMvc
				.perform(post("/books/").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(bookDto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertEquals("Book Addition Unsuccessful", response);
	}
	
	@Test
	void deleteBookTest() throws Exception {
		when(bookService.deleteBook(1)).thenReturn(true);
		MvcResult result = mockMvc.perform(delete("/books/1"))
				.andExpect(status().isAccepted()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertEquals("Book Deleted Successfully", response);
	}

	@Test
	void deleteBookErrorTest() throws Exception {
		when(bookService.deleteBook(2)).thenReturn(false);
		MvcResult result = mockMvc.perform(delete("/books/2"))
				.andExpect(status().isNotFound()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertEquals("Book Deletion Unsuccessful", response);
	}
	
	@Test
	void getBookTest() throws Exception {
		Book book = new Book();
		book.setId(1);
		when(bookService.getBook(1)).thenReturn(book);

		MvcResult result = mockMvc.perform(get("/books/1"))
				.andExpect(status().isOk()).andReturn();
		int statusCode = result.getResponse().getStatus();
				
		assertEquals(200, statusCode);
	}
	
	@Test
	void fetchAllBooksTest() throws Exception {
		List<Book> books = new ArrayList<>();
		Book book = new Book();
		book.setId(1);
		book.setName("bookname");
		book.setAuthor("bookauthor");
		book.setPublisher("bookpublisher");
		books.add(book);

		when(bookService.fetchAllBooks()).thenReturn(books);
		mockMvc.perform(get("/books")).andExpect(status().isOk()).andReturn();
	}
	
//	@Test
//	void updateBookTest() throws Exception {
////		BookDto bookDto = new BookDto();
//		HttpEntity<BookDto> book = new HttpEntity<>(bookDto);
//		when(bookService.updateBook(1,bookDto)).thenReturn(true);
//		MvcResult result = mockMvc.perform(put("/books/1").contentType(MediaType.APPLICATION_JSON)
//				.content(mapper.writeValueAsString(book)).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isAccepted()).andReturn();
//		String response = result.getResponse().getContentAsString();
//		assertEquals("Book Updated Successfully", response);
//	}

	@Test
	void updateBookErrorTest() throws Exception {
		when(bookService.updateBook(2,bookDto)).thenReturn(false);
		MvcResult result = mockMvc.perform(put("/books/2").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(bookDto)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertEquals("Book Updation Unsuccessful", response);
	}
	
}
