package com.example.application.service.feign;

import com.example.application.model.dto.Item;
import com.example.application.model.request.auth.AuthenticationRequest;
import com.example.application.model.request.auth.TokenValidationRequest;
import com.example.application.model.response.ApiResponse;
import com.example.application.model.response.ItemResponse;
import com.example.application.model.response.auth.TokenResponse;
import com.example.application.model.response.user.IssuedUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "auth-service", url = "http://localhost:9080")
public interface BookFeignClient {

    @PostMapping("/access/token")
    TokenResponse doLogin(@RequestBody AuthenticationRequest request);

    @PostMapping("/validate/token")
    IssuedUser validateToken(@RequestBody TokenValidationRequest request);

    @PostMapping("/books")
    ApiResponse<ItemResponse> createBook(@RequestBody Item request);

    @GetMapping("/books")
    ApiResponse<List<ItemResponse>> getAllBooks();
}
