package com.practice.batchscheduling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.batchscheduling.entity.Book;

public interface BookRepo extends JpaRepository<Book, Long> {

}
