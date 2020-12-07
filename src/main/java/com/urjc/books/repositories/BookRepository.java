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
                "Kika superbruja: y el libro de hechizos",
                "¿Quién es realmente la bruja Elviruja? ¿Por qué su libro de hechizos acabó en manos de Kika? " +
                        "¿Cómo influyó en ello el dragón Héctor? " +
                        "¿Y qué pintan el perverso brujo Jerónimo y el malaspulgas de su perro Serafín en esta historia?" +
                        " ¡Por fin vas a saberlo TODO sobre cómo empezaron las aventuras de Kika Superbruja!",
                "Knister",
				"Anaya",
                2008,
                new ArrayList<>());
        Book losPilaresDeLaTierraBook = new Book(getIdAndAdd(),
                "Los pilares de la Tierra",
                "Los pilares de la Tierra es una novela histórica del autor británico Ken Follett, " +
                        "ambientada en Inglaterra en la Edad Media, en concreto en el siglo XII, " +
                        "durante un periodo de guerra civil conocido como la anarquía inglesa, entre el hundimiento " +
                        "del White Ship y el asesinato del arzobispo Thomas Becket.",
                "Ken Follett",
                "Grupo Planeta",
                1990,
                new ArrayList<>());
        books.put(kikaSuperBrujaBook.getId(), kikaSuperBrujaBook);
        books.put(losPilaresDeLaTierraBook.getId(), losPilaresDeLaTierraBook);
    }
}
