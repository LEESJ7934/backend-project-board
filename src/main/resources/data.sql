INSERT INTO article (title, content, author, created_at, updated_at) VALUES ('제목1', '내용1', 'user 1', NOW(), NOW())
INSERT INTO comments (article_id, author, content, created_at) VALUES ('1','user 4', '댓글1', NOW())
INSERT INTO comments (article_id, author, content, created_at) VALUES ('1','user 5', '댓글2', NOW())
INSERT INTO article (title, content, author, created_at, updated_at) VALUES ('제목2', '내용2', 'user 2', NOW(), NULL)
