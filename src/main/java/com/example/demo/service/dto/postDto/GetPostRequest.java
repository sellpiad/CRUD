package com.example.demo.service.dto.postDto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetPostRequest {

    private int id;
    private String author;
    private String title;
    private String content;
    private Date createTime;
    private boolean isDeletable;
    private boolean isEditable;
}
