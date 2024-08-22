package me.leeseungjun.dto;

import lombok.Getter;
import me.leeseungjun.domain.Article;

//글 전체를 보여주기 위한 dto
@Getter
public class ArticleListViewResponse {
    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
