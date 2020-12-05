package com.urjc.books.controllers;

import com.urjc.books.models.Book;
import com.urjc.books.models.Comment;
import com.urjc.books.services.BookService;
import com.urjc.books.services.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestApiController {

    private BookService bookService;
    private CommentService commentService;

    public RestApiController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return this.bookService.findAll();
    }

    @GetMapping("/books/{bookId}")
    public Book getBook(@PathVariable Integer bookId) {
        return this.bookService.findById(bookId);
    }

    @DeleteMapping("/comments/{commentId}")
    public Comment deleteComment(Model model, @PathVariable Integer commentId) {
        Comment comment = this.commentService.delete(commentId);
        return comment;
    }
}
