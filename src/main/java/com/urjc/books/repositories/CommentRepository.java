package com.urjc.books.repositories;

import com.urjc.books.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByBookId(Long bookId);
}
