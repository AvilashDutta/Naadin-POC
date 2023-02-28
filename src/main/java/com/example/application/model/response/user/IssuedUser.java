package com.example.application.model.response.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IssuedUser {
    private long id;
    private String name;
}
