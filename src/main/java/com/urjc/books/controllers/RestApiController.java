package com.urjc.books.controllers;

import com.urjc.books.models.Book;
import com.urjc.books.models.Comment;
import com.urjc.books.services.BookService;
import com.urjc.books.services.CommentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/books")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) throws URISyntaxException {
        book = this.bookService.save(book);
        if (book != null) {
            //TODO Cambiar la URL para, poner el books/id
            return ResponseEntity.created(new URI("peticion_get_para_consultarlo")).body(book);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public Comment deleteComment(Model model, @PathVariable Integer commentId) {
        Comment comment = this.commentService.delete(commentId);
        return comment;
    }
}
