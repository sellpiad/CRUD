package com.example.demo.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Member extends BaseTime {
    
    private int memberNum;
    private String id;
    private String password;
}
