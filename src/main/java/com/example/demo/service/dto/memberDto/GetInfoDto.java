package com.example.demo.service.dto.memberDto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetInfoDto {
    
    private String username;
    private String rank;
    private Date createTime;
}
