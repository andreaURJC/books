package com.urjc.books.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.urjc.books.models.Book;
import com.urjc.books.models.User;
import com.urjc.books.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
