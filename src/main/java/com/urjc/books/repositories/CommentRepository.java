package com.urjc.books.repositories;

import com.urjc.books.models.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CommentRepository {

    private AtomicInteger atomicInteger;
    private ConcurrentMap<Integer, Comment> comments = new ConcurrentHashMap<Integer, Comment>();

    public CommentRepository() {
        int commentId = this.getIdAndAdd();
        comments.put(commentId, new Comment(commentId, "Andrea", "Me encanta leer, pedazo de libro", 10, 0));
        commentId = this.getIdAndAdd();
        comments.put(commentId, new Comment(commentId, "Juanma", "El mejor libro que he leído nunca", 2, 0));
        commentId = this.getIdAndAdd();
        comments.put(commentId, new Comment(commentId, "Antonio", "Un poco rara la historio, pero entretenida", 7, 0));
        commentId = this.getIdAndAdd();
        comments.put(commentId, new Comment(commentId, "Nacho", "Buen libro para los viajes entre partido y partido", 8, 1));
        commentId = this.getIdAndAdd();
        comments.put(commentId, new Comment(commentId, "Elisa", "Me ha ayudado mucho a relajarme", 9, 1));
    }

    public void save(Comment comment) {
        comments.put(this.getIdAndAdd(), comment);
    }

    public List<Comment> findByBookId(int bookId) {
        List<Comment> comentariosEncontrados = new ArrayList<>();

        for (Comment comment : comments.values()) {
            if (comment.isSameId(bookId)) {
                comentariosEncontrados.add(comment);
            }
        }
        return comentariosEncontrados;
    }

    public Comment delete(int commentId) {
        return comments.remove(commentId);
    }


    public int getIdAndAdd() {
        return atomicInteger.getAndAdd(1);
    }
}
