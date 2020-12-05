package com.urjc.books.controllers;

import com.urjc.books.models.Book;
import com.urjc.books.services.BookService;
import com.urjc.books.services.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebController {

    private BookService bookService;
    private CommentService commentService;

    public WebController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String getBooks(Model model) {
        model.addAttribute("books", this.bookService.findAll());
        return "index";
    }

    @GetMapping("/books/{bookId}/get")
    public String getBook(Model model, @PathVariable Integer bookId) {
        Book book = this.bookService.findById(bookId);
        model.addAttribute("bookId", book.getId());
        model.addAttribute("bookTitle", book.getTitle());
        model.addAttribute("bookSummary", book.getSummary());
        model.addAttribute("bookAuthor", book.getAuthor());
        model.addAttribute("bookPostYear", book.getPostYear());
        model.addAttribute("bookHasComments", book.hasComments());
        model.addAttribute("bookComments", book.getComments());
        return "book";
    }

    @PostMapping("/books/post")
    public String createBook(Model model, Book book) {
        this.bookService.save(book);
        return "redirect:/";
    }
}
