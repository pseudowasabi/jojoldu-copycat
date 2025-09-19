package com.pseudowasabi.copycat_webservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pseudowasabi.copycat_webservice.domain.posts.Posts;
import com.pseudowasabi.copycat_webservice.domain.posts.PostsRepository;
import com.pseudowasabi.copycat_webservice.web.dto.PostsResponseDto;
import com.pseudowasabi.copycat_webservice.web.dto.PostsSaveRequestDto;
import com.pseudowasabi.copycat_webservice.web.dto.PostsUpdateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void Posts는_등록된다() throws Exception {

        // given
        String title = "title";
        String content = "content";
        String author = "pseudowasabi@kakao.com";

        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String uri = "http://localhost:" + port + "/api/v1/posts";

        // when
//        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(uri, postsSaveRequestDto, Long.class);
        mockMvc.perform(
                post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postsSaveRequestDto))
        ).andExpect(status().isOk());

        // then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
        assertThat(postsList.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    @WithMockUser(roles = "USER")
    void Posts는_수정된다() throws Exception {

        // given
        String title = "title";
        String content = "content";
        String author = "pseudowasabi@kakao.com";

        Posts savedPost = postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .author(author)
                        .build());

        Long postId = savedPost.getId();
        String modifiedTitle = "new title";
        String modifiedContent = "new contents";

        PostsUpdateRequestDto postsUpdateRequestDto = PostsUpdateRequestDto.builder()
                .title(modifiedTitle)
                .content(modifiedContent)
                .build();

        String uri = "http://localhost:" + port + "/api/v1/posts/" + postId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(postsUpdateRequestDto);

        // when
//        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Long.class);
        mockMvc.perform(
                put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postsUpdateRequestDto))
        ).andExpect(status().isOk());

        // then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Optional<Posts> postsList = postsRepository.findById(postId);
        assertThat(postsList.get().getTitle()).isEqualTo(modifiedTitle);
        assertThat(postsList.get().getContent()).isEqualTo(modifiedContent);
        assertThat(postsList.get().getAuthor()).isEqualTo(author);
    }

    @Test
    @WithMockUser(roles = "USER")
    void Posts는_조회된다() throws Exception {

        // given
        String title1 = "first title";
        String content1 = "first content";
        String author1 = "pseudowasabi@kakao.com";

        Posts savedPost1 = postsRepository.save(Posts.builder()
                .title(title1)
                .content(content1)
                .author(author1)
                .build());

        Long postId1 = savedPost1.getId();

        String title2 = "second title";
        String content2 = "second content";
        String author2 = "ringoxcoffee@naver.com";

        Posts savedPost2 = postsRepository.save(Posts.builder()
                .title(title2)
                .content(content2)
                .author(author2)
                .build());

        Long postId2 = savedPost2.getId();

        String uri1 = "http://localhost:" + port + "/api/v1/posts/" + postId1;
        String uri2 = "http://localhost:" + port + "/api/v1/posts/" + postId2;

        // when
//        ResponseEntity<PostsResponseDto> responseEntity1 = testRestTemplate.getForEntity(uri1, PostsResponseDto.class);
//        ResponseEntity<PostsResponseDto> responseEntity2 = testRestTemplate.getForEntity(uri2, PostsResponseDto.class);
        String response1 = mockMvc.perform(get(uri1))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String response2 = mockMvc.perform(get(uri2))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

//        log.info("TEST {}", response1);

        ObjectMapper objectMapper = new ObjectMapper();
        PostsResponseDto postsResponseDto1 = objectMapper.readValue(response1, PostsResponseDto.class);
        PostsResponseDto postsResponseDto2 = objectMapper.readValue(response2, PostsResponseDto.class);

        // then
//        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);

//        assertThat(responseEntity1.getBody().getTitle()).isEqualTo(title1);
//        assertThat(responseEntity1.getBody().getContent()).isEqualTo(content1);
//        assertThat(responseEntity1.getBody().getAuthor()).isEqualTo(author1);

//        assertThat(responseEntity2.getBody().getTitle()).isEqualTo(title2);
//        assertThat(responseEntity2.getBody().getContent()).isEqualTo(content2);
//        assertThat(responseEntity2.getBody().getAuthor()).isEqualTo(author2);

        assertThat(postsResponseDto1.getTitle()).isEqualTo(title1);
        assertThat(postsResponseDto1.getContent()).isEqualTo(content1);
        assertThat(postsResponseDto1.getAuthor()).isEqualTo(author1);

        assertThat(postsResponseDto2.getTitle()).isEqualTo(title2);
        assertThat(postsResponseDto2.getContent()).isEqualTo(content2);
        assertThat(postsResponseDto2.getAuthor()).isEqualTo(author2);
    }
}