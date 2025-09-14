package com.pseudowasabi.copycat_webservice.config.auth.dto;

import com.pseudowasabi.copycat_webservice.domain.user.Users;
import lombok.Getter;

import java.io.Serializable;

@Getter
/**
 * Separate SessionUser from User to ensure serializable attribute
 */
public class SessionUsers implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUsers(Users users) {
        this.name = users.getName();
        this.email = users.getEmail();
        this.picture = users.getPicture();
    }
}
