package com.pseudowasabi.copycat_webservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void 메인_페이지_로딩() {

        // given
        String defaultText = "copycat-webservice";

        // when
        String body = this.testRestTemplate.getForObject("/", String.class);

        // then
        assertThat(body).contains(defaultText);
    }

}