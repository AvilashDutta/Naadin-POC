package com.example.application.model.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest implements Serializable {
    @JsonProperty("user_name")
    private String username;
    @JsonProperty("password")
    private String password;
}
