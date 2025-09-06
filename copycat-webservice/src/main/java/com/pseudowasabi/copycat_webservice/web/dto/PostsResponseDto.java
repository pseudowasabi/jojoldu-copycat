package com.pseudowasabi.copycat_webservice.web.dto;

import com.pseudowasabi.copycat_webservice.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.author = posts.getAuthor();
    }
}
