package me.leeseungjun.controller;


import lombok.RequiredArgsConstructor;
import me.leeseungjun.domain.Article;
import me.leeseungjun.domain.Comment;
import me.leeseungjun.dto.*;
import me.leeseungjun.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
@RestController //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    //HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody @Validated AddArticleRequest request, Principal principal) {
        Article savedArticle = blogService.save(request, principal.getName());

        //요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    //전체 게시글 조회
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    //글 하나 조회
    @GetMapping("/api/articles/{id}") //
    // URL 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable(name = "id") long id) { //컨트롤러에서 파라미터 이름에 대한 정보만 추가 (name = "id")를 붙여야 오류 발생x 식별을 못함
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }



    //글 삭제
    @DeleteMapping("/api/articles/{id}")
        public ResponseEntity<Void> deleteArticle(@PathVariable(name = "id") long id) {
            blogService.delete(id);

            return ResponseEntity.ok()
                    .build();

    }

    //글 수정
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable(name = "id") long id, @RequestBody UpdateArticleRequest request){
        Article updateArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updateArticle);
    }

    //댓글 생성
    @PostMapping("/api/comments")
    public ResponseEntity<AddCommentResponse> addComment(@RequestBody AddCommentRequest request, Principal principal){
        Comment savedComment = blogService.addComment(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AddCommentResponse(savedComment));
    }

    //댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "id") long id, Principal principal) {
        blogService.deleteComment(id, principal.getName());
        return ResponseEntity.ok()
                .build();
    }


    //댓글 수정
    @PutMapping("/api/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(name = "id") long id, @RequestBody UpdateCommentRequest request){
        Comment updateComment = blogService.update(id,request);

        return ResponseEntity.ok()
                .body(updateComment);
    }
}
