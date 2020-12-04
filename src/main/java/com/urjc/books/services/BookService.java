package com.urjc.books.services;

import com.urjc.books.models.Book;
import com.urjc.books.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    public Book findById(int id) {
        return this.bookRepository.findById(id);
    }

    public Book delete(int id) {
        return this.bookRepository.delete(id);
    }

    public void save(Book book) {
        this.bookRepository.save(book);
    }

}
