package com.pseudowasabi.copycat_webservice.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

//@RunWith(SpringRunner.class) // this is for junit4
//@ExtendWith(SpringExtension.class) // this is for junit5, but included in @SpringBootTest
@SpringBootTest
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

        postsRepository.save(Posts.builder()
                        .title(title)
                        .content(content)
                        .author("pseudowasabi@kakao.com")
                        .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertEquals()
    }
}