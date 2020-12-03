package com.urjc.books.models;

import lombok.Data;

@Data
public class Comment {

    private String author;
    private String text;
    private int score;
}
