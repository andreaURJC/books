package com.urjc.books.controllers;

import com.urjc.books.models.Book;
import com.urjc.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebController {
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String getBooks(Model model) {
        model.addAttribute("books", this.bookService.findAll());
        return "index";
    }

    @GetMapping("/books/{bookId}/get")
    public String getBook(Model model, Integer bookId) {
        Book book = this.bookService.findById(bookId);
        model.addAttribute("id", book.getId());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("summary", book.getSummary());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("postYear", book.getPostYear());
        model.addAttribute("comments", book.getComments());
        return "book";
    }

    @PostMapping("/books/post")
    public String createBook(Model model, Book book) {
        this.bookService.save(book);
        return "redirect:/";
    }
}
