package com.urjc.books.controllers;

import com.urjc.books.models.Book;
import com.urjc.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private BookService bookService;

    @GetMapping("/home")
    @ResponseBody
    public String getBooks(Model model) {
        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        return "home_page_template";
    }
}
