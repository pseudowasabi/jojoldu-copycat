package com.pseudowasabi.copycat_webservice.web;

import com.pseudowasabi.copycat_webservice.domain.posts.Posts;
import com.pseudowasabi.copycat_webservice.domain.posts.PostsRepository;
import com.pseudowasabi.copycat_webservice.web.dto.PostsSaveRequestDto;
import com.pseudowasabi.copycat_webservice.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
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
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(uri, postsSaveRequestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
        assertThat(postsList.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
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
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        Optional<Posts> postsList = postsRepository.findById(postId);
        assertThat(postsList.get().getTitle()).isEqualTo(modifiedTitle);
        assertThat(postsList.get().getContent()).isEqualTo(modifiedContent);
        assertThat(postsList.get().getAuthor()).isEqualTo(author);
    }
}