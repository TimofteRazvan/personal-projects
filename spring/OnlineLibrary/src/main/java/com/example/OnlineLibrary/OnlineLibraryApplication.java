package com.example.OnlineLibrary;

import com.example.OnlineLibrary.entity.Author;
import com.example.OnlineLibrary.entity.Book;
import com.example.OnlineLibrary.entity.Category;
import com.example.OnlineLibrary.entity.Publisher;
import com.example.OnlineLibrary.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineLibraryApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnlineLibraryApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialCreate(BookService bookService) {
		return(args) -> {
			Book book1 = new Book("ISBN1", "BOOK1", "First Book");
			Author author1 = new Author("AUTHOR1", "First Author");
			Category category1 = new Category("BUSINESS");
			Publisher publisher1 = new Publisher("PUBLISHER1");
			book1.addAuthor(author1);
			book1.addCategory(category1);
			book1.addPublisher(publisher1);
			bookService.createBook(book1);

			Book book2 = new Book("ISBN2", "BOOK2", "Second Book");
			Author author2 = new Author("AUTHOR2", "Second Author");
			Category category2 = new Category("SCIENCE");
			Publisher publisher2 = new Publisher("PUBLISHER2");
			book2.addAuthor(author2);
			book2.addCategory(category2);
			book2.addPublisher(publisher2);
			bookService.createBook(book2);

			Book book3 = new Book("ISBN3", "BOOK3", "Third Book");
			Author author3 = new Author("AUTHOR3", "Third Author");
			Category category3 = new Category("FICTION");
			Publisher publisher3 = new Publisher("PUBLISHER3");
			book3.addAuthor(author3);
			book3.addCategory(category3);
			book3.addPublisher(publisher3);
			bookService.createBook(book3);
		};
	}
}
