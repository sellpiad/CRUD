package com.example.demo.service.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdatePostRequest {
    
    private int id;
    private String author;
    private String title;
    private String content;
    
}
