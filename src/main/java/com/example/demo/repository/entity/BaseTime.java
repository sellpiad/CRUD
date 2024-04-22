package com.example.demo.repository.entity;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseTime {

    private Date createTime;
    private Date updateTime;
    
} 