package com.example.demo.service.dto.memberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateMemberRequest {
    private String id;
    private String password;
}
