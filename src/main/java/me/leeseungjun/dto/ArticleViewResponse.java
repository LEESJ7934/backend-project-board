package me.leeseungjun.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.leeseungjun.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String author;

    public ArticleViewResponse(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = LocalDateTime.now();
        this.author = article.getAuthor();
    }
}
