package me.leeseungjun.controller;


import lombok.RequiredArgsConstructor;
import me.leeseungjun.domain.Article;
import me.leeseungjun.domain.Comment;
import me.leeseungjun.dto.*;
import me.leeseungjun.repository.CommentRepository;
import me.leeseungjun.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;
    private final CommentRepository commentRepository;


    //전체 게시글 목록(페이징 기능 추가)
    @GetMapping("/articles")
    public String getArticles(Model model, @RequestParam(value="page", defaultValue="0") int page) { //기본 페이지 값을 0으로 추가

        Page<Article> paging = this.blogService.getList(page);
        model.addAttribute("paging", paging);

        return "articleList";

    }

    //게시글
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable(name = "id") long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    //생성, 수정 게시글
    @GetMapping("/new-article")
    // id 키를 가진 쿼리 파라미터 값을 id 변수에 매핑한다. 값이 없을 수도 있음
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) { //id가 없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else { //id가 없으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";

    }
    //수정 댓글
    @GetMapping("/new-comment")

    public String newComment(@RequestParam(required = false) Long id, Model model) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found: " + id));
            model.addAttribute("comment", new CommentViewResponse(comment));
        return "newComment";

    }
}

