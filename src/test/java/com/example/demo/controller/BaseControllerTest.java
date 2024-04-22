package com.example.demo.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.service.PostService;
import com.example.demo.service.dto.CreatePostRequest;
import com.example.demo.service.dto.GetListRequest;
import com.example.demo.service.dto.GetPostRequest;
import com.example.demo.service.dto.UpdatePostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BaseController.class)
class BaseControllerTest {

    // BaseController에서 잡고 있는 servcie 객체에 대한 mockbean
    @MockBean
    PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper obejctMapper;

    @Test
    @DisplayName("게시판 리스트 얻어오기")
    void getListTest() throws Exception {

        List<GetListRequest> list = new ArrayList<>();

        list.add(new GetListRequest(1, "test", "test", Date.valueOf("2024-04-21")));
        list.add(new GetListRequest(1, "test", "test", Date.valueOf("2024-04-21")));

        when(postService.getList()).thenReturn(list);

        mockMvc.perform(
                get("/getList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").exists())
                .andDo(print());

        verify(postService).getList();
    }

    @Test
    @DisplayName("포스트 생성")
    void createTest() throws Exception {
        CreatePostRequest createPostRequest = new CreatePostRequest("test", "haha", "man");
        String content = obejctMapper.writeValueAsString(createPostRequest);

        when(postService.createPost(createPostRequest))
                .thenReturn("success");

        mockMvc.perform(
                post("/createPost")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());

        verify(postService).createPost(createPostRequest);
    }

    @Test
    @DisplayName("포스트 업데이트")
    void updateTest() throws Exception {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest(1,"haha","haha", "haha");
        String content = obejctMapper.writeValueAsString(updatePostRequest);

        when(postService.updatePost(updatePostRequest)).thenReturn("success");

        mockMvc.perform(
                post("/updatePost?id=" + updatePostRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());

        verify(postService).updatePost(updatePostRequest);

    }

    @Test
    @DisplayName("포스트 삭제")
    void deleteTest() throws Exception {
        int postId = 1;

        when(postService.deletePost(postId)).thenReturn("success");

        mockMvc.perform(
                get("/deletePost?id=" + postId))
                .andExpect(status().isOk())
                .andDo(print());

        verify(postService).deletePost(postId);

    }

    @Test
    @DisplayName("포스트 얻어오기")
    void getPostTest() throws Exception {

        int postId = 0;

        // postService가 클라이언트로부터 아이디값을 전송 받았을 때 예상값
        when(postService.getPost(postId))
                .thenReturn(new GetPostRequest(1, "test", "tset", "test", Date.valueOf("2024-04-21")));

        // 기대값(클라이언트에게 전송하는 response)이 나왔는지 확인
        mockMvc.perform(
                get("/getPost?id=" + postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.author").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.date").exists())
                .andDo(print());

        // 제대로 실행됐는지 체크
        verify(postService).getPost(postId);

    }

}
