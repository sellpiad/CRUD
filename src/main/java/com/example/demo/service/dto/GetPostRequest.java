package com.example.demo.service.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetPostRequest {

    private int id;
    private String author;
    private String title;
    private String content;
    private Date createTime;

    
}
