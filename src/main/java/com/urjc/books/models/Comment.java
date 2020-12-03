package com.urjc.books.models;

import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private int id;
    private String author;
    private String text;
    private int score;
    private int bookId;

    public boolean isSameId(int id) {
        return this.id == id;
    }
}
