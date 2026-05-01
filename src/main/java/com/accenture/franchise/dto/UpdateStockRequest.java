package com.accenture.franchise.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateStockRequest {

    @Min(0)
    private Integer stock;

}
