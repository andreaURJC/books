package com.urjc.books.models;

import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private String summary;
    private String author;
    private int postYear;
    private List<Comment> comments;

    public boolean hasComments() {
        return !CollectionUtils.isEmpty(comments);
    }
}
