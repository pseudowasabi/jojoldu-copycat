package com.pseudowasabi.copycat_webservice.config.auth.dto;

import com.pseudowasabi.copycat_webservice.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
/**
 * Separate SessionUser from User to ensure serializable attribute
 */
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
