package com.urjc.books.controllers;

import com.urjc.books.models.Book;
import com.urjc.books.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/v1")
public class RestApiController {

    private BookService bookService;

    public RestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return this.bookService.findAll();
    }

    @GetMapping("/books/{bookId}")
    public Book getBook(@PathVariable Integer bookId) {
        return this.bookService.findById(bookId);
    }
}
