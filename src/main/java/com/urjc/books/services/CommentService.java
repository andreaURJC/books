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

    public Comment findById(int commentId) {
        return this.commentRepository.findById(commentId);
    }

    public List<Comment> findByBookId(int bookId) {
        return this.commentRepository.findByBookId(bookId);
    }

    public Comment save(Comment comment) {
        return this.commentRepository.save(comment);
    }

    public Comment delete(int commentId) {
        return this.commentRepository.delete(commentId);
    }

    public Comment findByIdAndBookId(int bookId, int commentId) {
        List<Comment> comments = this.findByBookId(bookId);
        return comments.stream().filter(comment -> comment.getId() == commentId).findFirst().orElse(null);
    }
}
