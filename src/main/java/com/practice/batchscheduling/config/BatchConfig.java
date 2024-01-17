package com.practice.batchscheduling.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.practice.batchscheduling.batch.BookAuthorProcessor;
import com.practice.batchscheduling.batch.BookTitleProcessor;
import com.practice.batchscheduling.batch.BookWriter;
import com.practice.batchscheduling.entity.Book;

@Configuration
public class BatchConfig {
	
	@Bean
	public Job bookReaderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("bookReadJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(chunkStep(jobRepository, transactionManager))
				.build();
		
	}

	@Bean
	public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("bookReaderStep", jobRepository).<Book, Book>
					chunk(10,transactionManager)
					.reader(reader())
					.processor(processor())
					.writer(writer())
					.build();
	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<Book> reader() {
			return new FlatFileItemReaderBuilder<Book>()
						.name("bookReader")
						.resource(new ClassPathResource("book_data.csv"))
						.delimited()
						.names(new String[] {"title","author","year_of_publishing"})
						.fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
							setTargetType(Book.class);
						}})
						.linesToSkip(1)
			            .build();
	}
	
	@Bean
	@StepScope
	public ItemWriter<Book> writer() {
			return new BookWriter();
	}
	
	@Bean
	public ItemProcessor<Book, Book> processor(){
		CompositeItemProcessor<Book, Book> processor = new CompositeItemProcessor<>();
		processor.setDelegates(List.of(new BookTitleProcessor(), new BookAuthorProcessor()));
		return processor;
	}
}
