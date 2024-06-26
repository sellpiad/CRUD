package com.example.demo.repository.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Member extends BaseTime {
    
    private int memberNum;
    private String id;
    private String password;
    private Date createTime;
    
}
