package com.urjc.books.repositories;

import com.urjc.books.models.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BookRepository {

    private final AtomicInteger atomicInt = new AtomicInteger();
    private ConcurrentHashMap<Integer, Book> books = new ConcurrentHashMap<>();

    public BookRepository() {
        this.initBooks();
    }

    public List<Book> findAll() {
        return new ArrayList<>(this.books.values());
    }

    public Book findById(int id) {
        if (this.books.get(id) == null) {
            return null;
        }
        return this.books.get(id);
    }

    public Book delete(int id) {
        return books.remove(id);
    }

    public Book save(Book book) {
        int id = this.getIdAndAdd();
        book.setId(id);
        this.books.put(id, book);
        return book;
    }

    private int getIdAndAdd() {
        return atomicInt.getAndAdd(1);
    }

    private void initBooks() {
        Book kikaSuperBrujaBook = new Book(getIdAndAdd(),
                "Kika Superbruja",
                "Summary del libro",
                "Autor de kika superr bruja",
                1990,
                new ArrayList<>());
        Book losPilaresDeLaTierraBook = new Book(getIdAndAdd(),
                "Los pilares de la tierra",
                "Novela ambientada en la edad media",
                "Ken Follett",
                1990,
                new ArrayList<>());
        books.put(kikaSuperBrujaBook.getId(), kikaSuperBrujaBook);
        books.put(losPilaresDeLaTierraBook.getId(), losPilaresDeLaTierraBook);
    }
}
