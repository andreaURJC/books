package com.urjc.books.services;

import com.urjc.books.models.Book;
import com.urjc.books.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Book findById(int id) {
        Book book = this.bookRepository.findById(id);
        book.setComments(this.commentService.findByBookId(book.getId()));
        return book;
    }

    public Book delete(int id) {
        return this.bookRepository.delete(id);
    }

    public void save(Book book) {
        this.bookRepository.save(book);
    }

}
