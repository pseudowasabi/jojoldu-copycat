package com.pseudowasabi.copycat_webservice.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("select m from Posts m order by m.id desc")
    List<Posts> findAllDesc();
}
