package com.urjc.books.models.dtos.out;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentByUserOutDto {

    private String text;
    private int score;
    private Long bookId;
}