package com.pseudowasabi.copycat_webservice.domain.posts;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class) // this is for junit4
//@ExtendWith(SpringExtension.class) // this is for junit5, but included in @SpringBootTest
@SpringBootTest
@Slf4j
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_저장_불러오기() {

        // given
        String title = "테스트 게시글";
        String content = "테스트 내용물";
        String author = "pseudowasabi@kakao.com";

        postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .author(author)
                        .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        Assertions.assertEquals(title, posts.getTitle());
        Assertions.assertEquals(content, posts.getContent());
    }

    @Test
    public void BaseTimeEntity_등록() {

        // given
        LocalDateTime now = LocalDateTime.of(2025, 9, 11, 0, 0, 0);
        String title = "테스트 게시글";
        String content = "테스트 내용물";
        String author = "pseudowasabi@kakao.com";

        postsRepository.save(
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author(author)
                        .build()
        );

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        log.info("createdDateTime: {}, modifiedDateTime: {}", posts.getCreatedDateTime(), posts.getModifiedDateTime());

        assertThat(posts.getCreatedDateTime()).isAfter(now);
        assertThat(posts.getModifiedDateTime()).isAfter(now);
    }
}