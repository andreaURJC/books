package com.urjc.books.controllers;

import com.urjc.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @Autowired
    private BookService bookService;

    @GetMapping("/home")
    public String getBooks(Model model) {
        model.addAttribute("books", this.bookService.findAll());
        return "index";
    }
}
