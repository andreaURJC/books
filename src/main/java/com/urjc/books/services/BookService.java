package com.urjc.books.services;

import com.urjc.books.models.entities.Book;
import com.urjc.books.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private CommentService commentService;

    public BookService(BookRepository bookRepository, CommentService commentService) {
        this.bookRepository = bookRepository;
        this.commentService = commentService;
    }

    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    public void delete(Long id) {
        this.bookRepository.deleteById(id);
    }

    public Optional<Book> save(Book book) {
        return Optional.of(this.bookRepository.save(book));
    }

}
