package com.accenture.franchise.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductRequest {

    @NotBlank
    private String name;
    @Min(0)
    private Integer stock;

}
