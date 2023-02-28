package com.example.application.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaadin.flow.component.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Item {
    private String name;
    private String authorName;
    private String description;
    private int noOfCopy;
    private String releaseDate;
}
