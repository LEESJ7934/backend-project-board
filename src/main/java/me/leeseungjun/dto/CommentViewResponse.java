package me.leeseungjun.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.leeseungjun.domain.Comment;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CommentViewResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String author;


    public CommentViewResponse(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = LocalDateTime.now();
        this.author = comment.getAuthor();
    }
}