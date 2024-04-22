package com.example.demo.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Post;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String create(Post post) {

        String query = "INSERT INTO board_1 (title,content,author) value('"
                + post.getTitle() + "', '"
                + post.getContent() + "', '"
                + post.getAuthor() + "')";

        int result = this.jdbcTemplate.update(query);

        if(result==1){
            return "success";
        } else {
            return "failed";
        }
    }

    @Override
    public String update(Post post) {

        String query = "UPDATE board_1 SET "
                + "author = '" + post.getAuthor() + "', "
                + "title = '" + post.getTitle() + "', "
                + "content = '" + post.getContent() + "' "
                + "WHERE id = " + post.getId() + ";";

        int result = this.jdbcTemplate.update(query);

        if(result==0)
            return "failed";
        else{
            return "success";
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
    public String delete(int id) {

        String query = "DELETE FROM board_1 WHERE ID = " + id + ";";

        return this.jdbcTemplate.update(query) + "";
    }

}
