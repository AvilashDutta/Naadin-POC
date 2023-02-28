package com.example.application.service;

import com.example.application.model.dto.Item;
import com.example.application.model.response.ApiResponse;
import com.example.application.model.response.ItemResponse;
import com.example.application.service.feign.BookFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final BookFeignClient bookFeignClient;

    public List<ItemResponse> getItems() {

        try {
            ApiResponse<List<ItemResponse>> response = bookFeignClient.getAllBooks();

            if (Objects.isNull(response)
                    || response.getResponseCode() != HttpStatus.OK.value()
                    || Objects.isNull(response.getData())
            )
                return new ArrayList<>();


            return response.getData();
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public ItemResponse addItem(Item newItem) {
        try {
            ApiResponse<ItemResponse> response = bookFeignClient.createBook(newItem);

            if (Objects.isNull(response)
                    || response.getResponseCode() != HttpStatus.OK.value()
                    || Objects.isNull(response.getData())
            )
                return null;

            return response.getData();
        } catch(Exception ex){
                return null;
            }
        }
    }

