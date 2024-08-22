package me.leeseungjun.dto;

import lombok.Getter;
import me.leeseungjun.domain.Article;
//전체 글 목록을 조회하고 응답하는 역할
@Getter
public class ArticleResponse {

    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
