package com.pseudowasabi.copycat_webservice.service;

import com.pseudowasabi.copycat_webservice.domain.posts.Posts;
import com.pseudowasabi.copycat_webservice.domain.posts.PostsRepository;
import com.pseudowasabi.copycat_webservice.web.dto.PostsListResponseDto;
import com.pseudowasabi.copycat_webservice.web.dto.PostsResponseDto;
import com.pseudowasabi.copycat_webservice.web.dto.PostsSaveRequestDto;
import com.pseudowasabi.copycat_webservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) {
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto postsUpdateRequestDto) {

        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("The post could not be found. (id=" + id + ")")
        );

        posts.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {

        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("The post could not be found. (id=" + id + ")")
        );

        return new PostsResponseDto(posts);
    }

    @Transactional(readOnly = true) // improve retrieve time
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                // equivalent to below expression
//                .map(posts -> new PostsListResponseDto(posts))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("The post could not be found. (id=" + id + ")")
        );
        postsRepository.delete(posts);
    }
}
