package com.practice.batchscheduling.batch;

import org.springframework.batch.item.ItemProcessor;

import com.practice.batchscheduling.entity.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookAuthorProcessor implements ItemProcessor<Book, Book> {

	@Override
	public Book process(Book item) throws Exception {
		log.info("Processing author for {}", item);
		item.setAuthor(item.getAuthor());
		return item;
	}

}
