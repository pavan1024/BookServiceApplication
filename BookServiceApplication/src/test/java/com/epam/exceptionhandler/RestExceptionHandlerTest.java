package com.epam.exceptionhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.epam.dto.BookDto;
import com.epam.entity.Book;
import com.epam.exception.BookAlreadyExistsException;
import com.epam.exception.BookNotFoundException;
import com.epam.exception.NoBooksException;
import com.epam.repo.BookRepository;
import com.epam.service.BookService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureMockMvc
@SpringBootTest
class RestExceptionHandlerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	BookService bookService;
	
	@MockBean
	BookRepository bookRepository;
	
	
	BookDto bookDto;
	Book book;
	
	protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException,JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
	
	@BeforeEach
	void setUp() {
		bookDto = new BookDto();
		bookDto.setName("bookname");
		bookDto.setAuthor("bookauthor");
		bookDto.setPublisher("bookpublisher");
		bookDto.setId(1);
		
		book = new Book();
		book.setName("bookname");
		book.setAuthor("bookauthor");
		book.setPublisher("bookpublisher");
		book.setId(1);
	}
	
	@Test
	void handlerBookNotFoundExceptionTest() throws Exception {
		when(bookService.getBook(2)).thenThrow(new BookNotFoundException("Book Not Found"));
		MvcResult result = mockMvc.perform(get("/books/2"))
				.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		HashMap<String ,String> data = this.mapFromJson(response, HashMap.class);
		assertEquals("Book Not Found",data.get("error"));
	}
	
	@Test
	void handlerBookNotFoundExceptionTest2() throws Exception {
		when(bookService.fetchAllBooks()).thenThrow(new NoBooksException("Book Not Found"));
		MvcResult result = mockMvc.perform(get("/books/"))
				.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		HashMap<String ,String> data = this.mapFromJson(response, HashMap.class);
		assertEquals("Book Not Found",data.get("error"));
	}
	
//	@Test
//	void handlerBookAlreadyExistsExceptionTest() throws Exception {
////		Optional<Book> optionalBook = Optional.ofNullable(book);
////		when(bookRepository.findById(1)).thenReturn(optionalBook);
//		when(bookService.addBook(bookDto)).thenThrow(new BookAlreadyExistsException("Book Already Exists"));
//		MvcResult result = mockMvc.perform(post("/books/"))
//				.andExpect(status().isBadRequest()).andReturn();
//		String response = result.getResponse().getContentAsString();
//		HashMap<String ,String> data = this.mapFromJson(response, HashMap.class);
//		assertEquals("Book Already Exists",data.get("error"));
//	}
	
}
