package com.urjc.books.controllers;

import com.urjc.books.models.Book;
import com.urjc.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RestController {
    @Autowired
    private BookService bookService;

    //TODO: review endpoint with JUANMA
    @GetMapping("/books")
    @ResponseBody
    public List<Book> getBooks() {
        return this.bookService.findAll();
    }
}
