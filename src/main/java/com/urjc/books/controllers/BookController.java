package com.urjc.books.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.urjc.books.models.entities.Book;
import com.urjc.books.models.entities.Comment;
import com.urjc.books.models.entities.User;
import com.urjc.books.services.BookService;
import com.urjc.books.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private CommentService commentService;

    public BookController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @PostConstruct
    public void init() {
        Book kikaSuperBrujaBook = new Book();
        kikaSuperBrujaBook.setTitle("Kika superbruja: y el libro de hechizos");
        kikaSuperBrujaBook.setSummary("¿Quién es realmente la bruja Elviruja? ¿Por qué su libro de hechizos acabó en manos de Kika? " +
                "¿Cómo influyó en ello el dragón Héctor? " +
                "¿Y qué pintan el perverso brujo Jerónimo y el malaspulgas de su perro Serafín en esta historia?" +
                " ¡Por fin vas a saberlo TODO sobre cómo empezaron las aventuras de Kika Superbruja!");
        kikaSuperBrujaBook.setAuthor("Knister");
        kikaSuperBrujaBook.setPublisher("Anaya");
        kikaSuperBrujaBook.setPostYear(2008);
        Book losPilaresDeLaTierraBook = new Book();
        losPilaresDeLaTierraBook.setTitle("Los pilares de la Tierra");
        losPilaresDeLaTierraBook.setSummary("Los pilares de la Tierra es una novela histórica del autor británico Ken Follett, " +
                "ambientada en Inglaterra en la Edad Media, en concreto en el siglo XII, " +
                "durante un periodo de guerra civil conocido como la anarquía inglesa, entre el hundimiento " +
                "del White Ship y el asesinato del arzobispo Thomas Becket.");
        losPilaresDeLaTierraBook.setAuthor("Ken Follett");
        losPilaresDeLaTierraBook.setPublisher("Grupo Planeta");
        losPilaresDeLaTierraBook.setPostYear(1990);

        Comment andreaComment = new Comment();
        andreaComment.setAuthor(new User());
        andreaComment.getAuthor().setNick("Andrea");
        andreaComment.getAuthor().setEmail("andea@urjc.es");
        andreaComment.setText("Me encanta leer, pedazo de libro");
        andreaComment.setScore(5);
        andreaComment.setBook(kikaSuperBrujaBook);

        Comment juanmaComment = new Comment();
        juanmaComment.setAuthor(new User());
        juanmaComment.getAuthor().setNick("Juanma");
        juanmaComment.getAuthor().setEmail("juanma@urjc.es");
        juanmaComment.setText("El mejor libro que he leído nunca");
        juanmaComment.setScore(1);
        juanmaComment.setBook(kikaSuperBrujaBook);

        Comment antonioComment = new Comment();
        antonioComment.setAuthor(new User());
        antonioComment.getAuthor().setNick("Antonio");
        antonioComment.getAuthor().setEmail("antonio@urjc.es");
        antonioComment.setText("Un poco rara la historia, pero entretenida");
        antonioComment.setScore(4);
        antonioComment.setBook(kikaSuperBrujaBook);

        var comentariosKika = Arrays.asList(andreaComment, juanmaComment, antonioComment);
        kikaSuperBrujaBook.setComments(comentariosKika);

        Comment nachoComment = new Comment();
        nachoComment.setAuthor(new User());
        nachoComment.getAuthor().setNick("Nacho");
        nachoComment.getAuthor().setEmail("nacho@urjc.es");
        nachoComment.setText("Buen libro para los viajes entre partido y partido");
        nachoComment.setScore(4);
        nachoComment.setBook(losPilaresDeLaTierraBook);

        Comment elisaComment = new Comment();
        elisaComment.setAuthor(new User());
        elisaComment.getAuthor().setNick("Elisa");
        elisaComment.getAuthor().setEmail("elisa@urjc.es");
        elisaComment.setText("Me ha ayudado mucho a relajarme");
        elisaComment.setScore(5);
        elisaComment.setBook(losPilaresDeLaTierraBook);

        var comentariosPilares = Arrays.asList(nachoComment, elisaComment);
        losPilaresDeLaTierraBook.setComments(comentariosPilares);

        this.bookService.save(kikaSuperBrujaBook);
        this.bookService.save(losPilaresDeLaTierraBook);
    }
    
    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Books found",
                    content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Book.class)
                            )}
            )
    })
    @JsonView(Book.IndexList.class)
    @GetMapping("/")
    public List<Book> getBooks() {
        return this.bookService.findAll();
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book found",
                    content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Book.class)
                            )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found"
            )
    })
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(
            @Parameter(description = "The id of the book to be searched")
            @PathVariable Long bookId) {
        // TODO : Crear un dto para devolver el libro con sus atributos y de los comentarios SOLO el texto, el nick y el email del Usuario
        Optional<Book> book = this.bookService.findById(bookId);

        return ResponseEntity.of(book);
    }

    @Operation(summary = "Create a book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Book created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid book supplied"
            )
    })
    @PostMapping("/")
    public ResponseEntity<Book> createBook(
            @Parameter(description = "The book to be created")
            @RequestBody Book book) {
        Optional<Book> savedBook = this.bookService.save(book);
        if (savedBook.isPresent()) {
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(savedBook.get().getId()).toUri();
            return ResponseEntity.created(location).body(savedBook.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Create a comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Comment created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid comment supplied"
            )
    })
    @PostMapping("/{bookId}/comments")
    public ResponseEntity<Comment> createComment(
            @Parameter(description = "The book to be commented")
            @PathVariable Long bookId,
            @Parameter(description = "The comment to be created")
            @RequestBody Comment comment) {
        // TODO : Crear DTO al que le pasemos además el nick del usuario que debe existir en la BBDD
        Optional<Book> book = this.bookService.findById(bookId);
        if (book.isPresent()) {
            comment.setBook(book.get());
            Optional<Comment> savedComment = this.commentService.save(comment);
            if (savedComment.isPresent()) {
                URI location = fromCurrentRequest().path("/{id}").buildAndExpand(savedComment.get().getId()).toUri();
                return ResponseEntity.created(location).body(savedComment.get());
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get a comment by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found"
            )
    })
    @GetMapping("/{bookId}/comments/{commentId}")
    public ResponseEntity<Comment> getComment(
            @Parameter(description = "The id of the book")
            @PathVariable Long bookId,
            @Parameter(description = "The id of the comment to be searched")
            @PathVariable Long commentId) {
        Optional<Comment> comment = this.commentService.findByIdAndBookId(bookId, commentId);
        return ResponseEntity.of(comment);
    }

    @Operation(summary = "Delete a comment by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Comment.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment already deleted"
            )
    })
    @DeleteMapping("/{bookId}/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(
            @Parameter(description = "The id of book")
            @PathVariable Long bookId,
            @Parameter(description = "The id of the comment to be deleted")
            @PathVariable Long commentId) {
        Optional<Comment> comment = this.commentService.findByIdAndBookId(bookId, commentId);
        if (comment.isPresent()) {
            this.commentService.delete(commentId);
        }
        return ResponseEntity.of(comment);
    }
}