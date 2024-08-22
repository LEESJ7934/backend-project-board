package me.leeseungjun.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.leeseungjun.domain.Article;
import me.leeseungjun.domain.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {
    private Long articleId;
    private String content;

    public Comment toEntity(String author, Article article){
        return Comment.builder()
                .article(article)
                .content(content)
                .author(author)
                .build();
    }
}
