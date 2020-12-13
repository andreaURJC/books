package com.urjc.books.models.dtos.out;

import com.urjc.books.models.entities.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentsByUserOutDto {

    private String userNick;
    private List<CommentByUserOutDto> comments = new ArrayList<>();
}
