package com.epam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "Books")
@Getter
@Setter
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BOOK_ID", updatable = false, nullable = false)
	int id;
	@Column(name = "BOOK_NAME", nullable = false)
	String name;
	@Column(name = "PUBLISHER", nullable = false)
	String publisher;
	@Column(name = "AUTHOR", nullable = false)
	String author;

}
