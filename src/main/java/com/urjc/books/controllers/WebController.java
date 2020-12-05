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

@Controller
public class WebController {

    private BookService bookService;
    private CommentService commentService;

    public WebController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/home")
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

    @GetMapping("/books/{bookId}/comments/{commentId}/delete")
    public String deleteComment(Model model, @PathVariable Integer bookId, @PathVariable Integer commentId) {
        this.commentService.delete(commentId);
        return "redirect:/books/" + bookId + "/get";
    }
}
