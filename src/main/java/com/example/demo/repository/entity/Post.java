package com.example.demo.repository.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTime{

    private int id;
    private String title;
    private String content;
    private String author;

    @Builder
    public Post(int id, String title, String content, String author, Date createTime){
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        setCreateTime(createTime);
    }
}
