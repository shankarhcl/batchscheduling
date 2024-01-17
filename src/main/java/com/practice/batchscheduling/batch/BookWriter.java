package com.practice.batchscheduling.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.practice.batchscheduling.entity.Book;
import com.practice.batchscheduling.repository.BookRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookWriter implements ItemWriter<Book>{

	@Autowired
	private BookRepo bookRepo;
	
	@Override
	public void write(Chunk<? extends Book> chunk) throws Exception {
		log.info("writing: {}", chunk.getItems().size());
		bookRepo.saveAll(chunk.getItems());
		
	}

}
