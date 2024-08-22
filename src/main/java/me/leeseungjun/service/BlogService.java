package me.leeseungjun.service;


import lombok.RequiredArgsConstructor;
import me.leeseungjun.config.error.exception.ArticleNotFoundException;
import me.leeseungjun.domain.Article;
import me.leeseungjun.domain.Comment;
import me.leeseungjun.dto.AddArticleRequest;
import me.leeseungjun.dto.AddCommentRequest;
import me.leeseungjun.dto.UpdateArticleRequest;
import me.leeseungjun.dto.UpdateCommentRequest;
import me.leeseungjun.repository.BlogRepository;
import me.leeseungjun.repository.CommentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;


    //게시판 목록을 페이지로
    public Page<Article> getList(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return this.blogRepository.findAll(pageable);
    }

    //게시판글 저장 기능
    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    //게시판 글 찾는 기능
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    //게시판 아이디로 찾기
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
    }


    //게시판 삭제
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional//중간에 값이 에러가 떠도 제대로 된 값 수정을 보장
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }


    @Transactional//중간에 값이 에러가 떠도 제대로 된 값 수정을 보장
    public Comment update(long id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        comment.update(request.getContent());

        return comment;
    }
    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

    //댓글 추가
    public Comment addComment(AddCommentRequest request, String userName){
        Article article = blogRepository.findById(request.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("not found: " + request.getArticleId()));
        return commentRepository.save(request.toEntity(userName, article));
    }

    public void deleteComment(long id, String userName) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found: " + id));

        // 작성자 확인
        if (!comment.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("You are not authorized to delete this comment");
        }
        commentRepository.delete(comment);
    }
}