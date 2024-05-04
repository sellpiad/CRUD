package com.example.demo.repository.postRepo;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepositoryImpl implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(Post post, int memberNum) {

        String query = "INSERT INTO board_1 (title,content,author,authorNum) value('"
                + post.getTitle() + "', '"
                + post.getContent() + "', '"
                + post.getAuthor() + "', "
                + memberNum + ")";

        if (this.jdbcTemplate.update(query) == 1)
            return true;
        else
            return false;

    }

    @Override
    public boolean update(Post post) {

        String query = "UPDATE board_1 SET "
                + "author = '" + post.getAuthor() + "', "
                + "title = '" + post.getTitle() + "', "
                + "content = '" + post.getContent() + "' "
                + "WHERE id = " + post.getId() + ";";

        if (this.jdbcTemplate.update(query) == 1)
            return true;
        else {
            return false;
        }
    }

    @Override
    public List<Post> findAll() {
        String query = "SELECT id,author,title,createTime FROM board_1;";

        return this.jdbcTemplate.query(query, boardMapper());
    }

    private RowMapper<Post> boardMapper() {
        return (rs, rowNum) -> {
            return Post.builder()
                    .id(rs.getInt("id"))
                    .author(rs.getString("author"))
                    .title(rs.getString("title"))
                    .createTime(rs.getDate("createTime"))
                    .build();
        };
    }

    @Override
    public Post findById(int id) {
        String query = "SELECT * from board_1 WHERE id=?;";

        return this.jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            return Post.builder()
                    .id(rs.getInt("id"))
                    .author(rs.getString("author"))
                    .title(rs.getString("title"))
                    .content(rs.getString("content"))
                    .createTime(rs.getDate("createTime"))
                    .build();
        }, id);

    }

    @Override
    public boolean delete(int id) {

        String query = "DELETE FROM board_1 WHERE ID = " + id + ";";

        if (this.jdbcTemplate.update(query) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("null")
    @Override
    public boolean memberCheck(int postNum, int memberNum) {

        String query = "SELECT authorNum FROM board_1 WHERE id=?";
        int authorNum = this.jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            return rs.getInt("authorNum");
        }, postNum);

        log.debug("postNum={}", postNum);
        log.debug("memberNum={}", memberNum);
        log.debug("authorNum={}", authorNum);

        if (authorNum == memberNum) {
            return true;
        } else
            return false;
    }

}
