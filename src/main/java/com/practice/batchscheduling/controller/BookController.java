package com.practice.batchscheduling.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.batchscheduling.entity.Book;
import com.practice.batchscheduling.repository.BookRepo;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookRepo bookRepo;
	
	@RequestMapping("/all")
	public List<Book> getAll(){
		return bookRepo.findAll();
	}

}
