package com.thubas.sotiras.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String title;
	private String imageUrl;
	private String description;
	private String publisher;
	private Double price;
	private Boolean canSwap;
	
	@ElementCollection(targetClass = java.lang.String.class)
	private List<String> authors = new ArrayList<>();
	
	@ElementCollection(targetClass = java.lang.String.class)
	private List<String> categories = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "book")
	private List<Review> reviews = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "book")
	private List<Vote> votes = new ArrayList<>();

	@OneToOne
	private User contributor;

	@Enumerated(value = EnumType.STRING)
	private BookStatus status;
	
	@Enumerated(value = EnumType.STRING)
	private BookCondition condition;
	
	private Instant creationDate;
}
