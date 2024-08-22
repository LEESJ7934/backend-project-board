package me.leeseungjun.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article{
        @Id //id 필드를 기본키로 지정
        @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
        @Column(name = "id", updatable = false)
        private Long id;

        @Column(name = "title", nullable = false)
        private String title;

        @Column(name = "content", nullable = false)
        private String content;

        @Column(name = "author", nullable = false)
        private String author;

        @Builder
        public Article(String author, String title, String content){
                this.author = author;
                this.title = title;
                this.content = content;
        }

        @Builder
        public Article(String title, String content){
        this.title=title;
        this.content=content;
        }

        public void update(String title, String content) {
                this.title = title;
                this.content = content;
        }

        @CreatedDate
        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @LastModifiedDate
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
        private List<Comment> comments;

}

