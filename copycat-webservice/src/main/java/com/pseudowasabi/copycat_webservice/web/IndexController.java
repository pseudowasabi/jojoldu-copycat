package com.pseudowasabi.copycat_webservice.web;

import com.pseudowasabi.copycat_webservice.config.auth.annotation.LoginUser;
import com.pseudowasabi.copycat_webservice.config.auth.dto.SessionUsers;
import com.pseudowasabi.copycat_webservice.service.PostsService;
import com.pseudowasabi.copycat_webservice.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUsers sessionUsers) {
        model.addAttribute("posts", postsService.findAllDesc());

        if (sessionUsers != null) {
            model.addAttribute("userName", sessionUsers.getName());
            model.addAttribute("userEmail", sessionUsers.getEmail());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id,
                              Model model) {
        PostsResponseDto postsResponseDto = postsService.findById(id);
        model.addAttribute("post", postsResponseDto);
        return "posts-update";
    }
}
