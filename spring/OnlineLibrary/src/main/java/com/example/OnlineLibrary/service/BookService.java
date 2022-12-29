package com.example.OnlineLibrary.service;

import com.example.OnlineLibrary.entity.Book;
import com.example.OnlineLibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found!"));
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public void removeBook(Long id) {
        // Have to check in case it doesn't exist and throw exception.
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found!"));
        bookRepository.deleteById(book.getId());
    }
}
