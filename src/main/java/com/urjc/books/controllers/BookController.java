package com.urjc.books.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.urjc.books.models.Book;
import com.urjc.books.models.Comment;
import com.urjc.books.models.User;
import com.urjc.books.services.BookService;
import com.urjc.books.services.CommentService;
import com.urjc.books.services.UserService;
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

@RestController("/books")
public class BookController {

    private BookService bookService;
    private CommentService commentService;
    private UserService userService;

    public BookController(BookService bookService, CommentService commentService, UserService userService) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.userService = userService;
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
        andreaComment.setAuthor("Andrea");
        andreaComment.setText("Me encanta leer, pedazo de libro");
        andreaComment.setScore(5);
        andreaComment.setBook(kikaSuperBrujaBook);

        Comment juanmaComment = new Comment();
        juanmaComment.setAuthor("Juanma");
        juanmaComment.setText("El mejor libro que he leído nunca");
        juanmaComment.setScore(1);
        juanmaComment.setBook(kikaSuperBrujaBook);

        Comment antonioComment = new Comment();
        antonioComment.setAuthor("Antonio");
        antonioComment.setText("Un poco rara la historia, pero entretenida");
        antonioComment.setScore(4);
        antonioComment.setBook(kikaSuperBrujaBook);

        var comentariosKika = Arrays.asList(andreaComment, juanmaComment, antonioComment);
        kikaSuperBrujaBook.setComments(comentariosKika);

        Comment nachoComment = new Comment();
        nachoComment.setAuthor("Nacho");
        nachoComment.setText("Buen libro para los viajes entre partido y partido");
        nachoComment.setScore(4);
        nachoComment.setBook(losPilaresDeLaTierraBook);

        Comment elisaComment = new Comment();
        elisaComment.setAuthor("Elisa");
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
        book = this.bookService.save(book);
        if (book != null) {
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
            return ResponseEntity.created(location).body(book);
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
        Optional<Book> book = this.bookService.findById(bookId);
        if (book.isPresent()) {
            comment.setBook(book.get());
            this.commentService.save(comment);
            if (comment != null) {
                URI location = fromCurrentRequest().path("/{id}").buildAndExpand(comment.getId()).toUri();
                return ResponseEntity.created(location).body(comment);
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

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user supplied"
            )
    })
    @PostMapping("/users")
    public ResponseEntity<User> createUser(
            @Parameter(description = "The user to be created")
            @RequestBody User user) {
        Optional<User> newUser = this.userService.save(user);
        if (newUser.isPresent()) {
            URI location = fromCurrentRequest().path("/{nick}").buildAndExpand(newUser.get().getNick()).toUri();
            return ResponseEntity.created(location).body(newUser.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get a user by the nick")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found"
            )
    })
    @GetMapping("/users/{nick}")
    public ResponseEntity<User> getUser(
            @Parameter(description = "The nick of the user to be searched")
            @PathVariable int nick) {
        Optional<User> user = this.userService.findUserByNick(nick);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )}
            )
    })
    @JsonView(Book.IndexList.class)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        Optional<List<User>> users = this.userService.findAll();
        
        if(users.isPresent()) {
            return ResponseEntity.ok().body(users.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}