package com.urjc.books.services;

import com.urjc.books.models.Comment;
import com.urjc.books.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findByBookId(int bookId) {
        return this.commentRepository.findByBookId(bookId);
    }
}
