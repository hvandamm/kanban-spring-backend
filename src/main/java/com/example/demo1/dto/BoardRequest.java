package com.example.demo1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BoardRequest {

    @NotBlank(message = "Board name is required")
    @Size(max = 50, message = "Board name must not exceed 50 characters")
    private String name;
}