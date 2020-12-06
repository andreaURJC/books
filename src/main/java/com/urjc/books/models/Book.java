package com.urjc.books.models;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    public interface IndexList {}

    @JsonView(IndexList.class)
    private int id;
    @JsonView(IndexList.class)
    private String title;
    private String summary;
    private String author;
    private int postYear;
    private List<Comment> comments = new ArrayList<>();
}
