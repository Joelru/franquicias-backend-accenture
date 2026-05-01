package com.accenture.franchise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopStockResponse {

    private String branchName;
    private String productName;
    private Integer stock;

}
