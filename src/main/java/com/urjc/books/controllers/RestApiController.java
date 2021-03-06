package com.urjc.books.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.urjc.books.models.Book;
import com.urjc.books.models.Comment;
import com.urjc.books.services.BookService;
import com.urjc.books.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
public class RestApiController {

    private BookService bookService;
    private CommentService commentService;

    public RestApiController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
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
    @GetMapping("/books")
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
    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(
            @Parameter(description = "The id of the book to be searched")
            @PathVariable Integer bookId) {
        Book book = this.bookService.findById(bookId);
        if (book != null) {
            return ResponseEntity.ok().body(book);
        }
        return ResponseEntity.notFound().build();
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
    @PostMapping("/books")
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
    @PostMapping("/books/{bookId}/comments")
    public ResponseEntity<Comment> createComment(
            @Parameter(description = "The book to be commented")
            @PathVariable Integer bookId,
            @Parameter(description = "The comment to be created")
            @RequestBody Comment comment) {
        comment.setBookId(bookId);
        if (this.bookService.findById(bookId) != null) {
            comment = this.commentService.save(comment);
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
    @GetMapping("/books/{bookId}/comments/{commentId}")
    public ResponseEntity<Comment> getComment(
            @Parameter(description = "The id of the book")
            @PathVariable Integer bookId,
            @Parameter(description = "The id of the comment to be searched")
            @PathVariable Integer commentId) {
        Comment comment = this.commentService.findByIdAndBookId(bookId, commentId);
        if (comment != null) {
            return ResponseEntity.ok().body(comment);
        }
        return ResponseEntity.notFound().build();
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
    @DeleteMapping("/books/{bookId}/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(
            @Parameter(description = "The id of book")
            @PathVariable Integer bookId,
            @Parameter(description = "The id of the comment to be deleted")
            @PathVariable Integer commentId) {
        Comment comment = this.commentService.findByIdAndBookId(bookId, commentId);
        if (comment != null) {
            comment = this.commentService.delete(commentId);
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.notFound().build();
    }
}