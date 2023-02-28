package com.example.application.service;

import com.example.application.model.request.auth.AuthenticationRequest;
import com.example.application.model.request.auth.TokenValidationRequest;
import com.example.application.model.response.auth.TokenResponse;
import com.example.application.model.response.user.IssuedUser;
import com.example.application.service.feign.BookFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final BookFeignClient bookFeignClient;

    public String getAuthToken(String userName, String password) throws Exception {

        try {
            TokenResponse response = bookFeignClient.doLogin(
                    AuthenticationRequest
                            .builder()
                            .username(userName)
                            .password(password)
                            .build()
            );

            if (Objects.isNull(response) || response.getToken().isEmpty()) throw new Exception();

            return response.getToken();
        } catch (Exception ex){
            ex.printStackTrace();
            throw new Exception();
        }
    }

    public boolean isAuthenticated(String token) {
        IssuedUser response = bookFeignClient.validateToken(new TokenValidationRequest("Bearer ".concat(token)));
        return !Objects.isNull(response) && !response.getName().isEmpty();
    }
}
