package com.example.demo.service.dto.postDto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetListRequest {

    private int id;
    private String title;
    private String author;
    private Date createTime;

}
