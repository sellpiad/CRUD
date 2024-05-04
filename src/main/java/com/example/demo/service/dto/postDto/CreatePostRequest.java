package com.example.demo.service.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreatePostRequest {
    
    private String title;
    private String content;
    private String author;

}
