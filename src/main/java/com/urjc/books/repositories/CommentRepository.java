package com.urjc.books.repositories;

import com.urjc.books.models.Comment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CommentRepository {

    private AtomicInteger atomicInt = new AtomicInteger();
    private ConcurrentMap<Integer, Comment> comments = new ConcurrentHashMap<Integer, Comment>();

    public CommentRepository() {
        int commentId = this.getIdAndAdd();
        this.comments.put(commentId, new Comment(commentId, "Andrea", "Me encanta leer, pedazo de libro", 5, 0));
        commentId = this.getIdAndAdd();
        this.comments.put(commentId, new Comment(commentId, "Juanma", "El mejor libro que he leído nunca", 1, 0));
        commentId = this.getIdAndAdd();
        this.comments.put(commentId, new Comment(commentId, "Antonio", "Un poco rara la historio, pero entretenida", 4, 0));
        commentId = this.getIdAndAdd();
        this.comments.put(commentId, new Comment(commentId, "Nacho", "Buen libro para los viajes entre partido y partido", 4, 1));
        commentId = this.getIdAndAdd();
        this.comments.put(commentId, new Comment(commentId, "Elisa", "Me ha ayudado mucho a relajarme", 1, 1));
    }

    public Comment save(Comment comment) {
        int commentId = this.getIdAndAdd();
        comment.setId(commentId);
        this.comments.put(commentId, comment);
        return comment;
    }

    public Comment findById(int commentId) {
        return this.comments.get(commentId);
    }

    public List<Comment> findByBookId(int bookId) {
        List<Comment> comentariosEncontrados = new ArrayList<>();

        for (Comment comment : this.comments.values()) {
            if (comment.isSameBookId(bookId)) {
                comentariosEncontrados.add(comment);
            }
        }
        return comentariosEncontrados;
    }

    public Comment delete(int commentId) {
        return this.comments.remove(commentId);
    }


    private int getIdAndAdd() {
        return this.atomicInt.getAndAdd(1);
    }
}
