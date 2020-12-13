package com.urjc.books.models.entities;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book {
    public interface IndexList {}

    //TODO: Refactor: Quitar la interfaz IndexList y hacer que devuelva un DTO
    @JsonView(IndexList.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @JsonView(IndexList.class)
    private String title;

    @Column(length = 400)
    private String summary;
    private String author;
    private String publisher;
    private int postYear;

    @OneToMany(mappedBy = "book", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPostYear() {
        return postYear;
    }

    public void setPostYear(int postYear) {
        this.postYear = postYear;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
