package com.urjc.books.services;

import com.urjc.books.models.dtos.out.GetCommentsByUserOutDto;
import com.urjc.books.models.entities.Comment;
import com.urjc.books.models.entities.User;
import com.urjc.books.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> save(User user) {
        return Optional.of(this.userRepository.save(user));
    }

    public Optional<User> findById(String nick) {
        return this.userRepository.findById(nick);
    }

    public void delete(User user) {
        this.userRepository.delete(user);
    }

    public GetCommentsByUserOutDto getCommentsByUser(User user) {
        GetCommentsByUserOutDto outDto = new GetCommentsByUserOutDto();
        outDto.setUserNick(user.getNick());
        GetCommentsByUserOutDto.CommentByUserOutDto newComment;
        for (Comment comment : user.getComments()) {
            newComment = GetCommentsByUserOutDto.CommentByUserOutDto
                            .builder()
                            .text(comment.getText())
                            .score(comment.getScore())
                            .bookId(comment.getBook().getId())
                            .build();
            outDto.getComments().add(newComment);
        }
        return outDto;
    }
}
