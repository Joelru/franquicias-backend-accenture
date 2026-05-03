package com.accenture.franchise.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBranchRequest {

    @NotBlank(message = "Name is required")
    private String name;


}