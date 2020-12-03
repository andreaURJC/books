package com.urjc.books.models;

import lombok.Data;

import java.util.List;

@Data
public class Book {
    private String title;
    private String summary;
    private String author;
    private int postYear;
    private List<Comment> comments;

}
