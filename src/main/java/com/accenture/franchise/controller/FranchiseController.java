package com.accenture.franchise.controller;

import com.accenture.franchise.dto.*;
import com.accenture.franchise.model.Franchise;
import com.accenture.franchise.service.FranchiseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService service;

    @PostMapping
    public Mono<Franchise> create(@Valid @RequestBody CreateFranchiseRequest request) {
        return service.createFranchise(request);
    }

    @PutMapping("/{franchiseId}/name")
    public Mono<Franchise> updateFranchiseName(
            @PathVariable String franchiseId,
            @Valid @RequestBody UpdateNameRequest request) {

        return service.updateFranchiseName(franchiseId, request);
    }

    @PostMapping("/{franchiseId}/branches")
    public Mono<Franchise> addBranch(
            @PathVariable String franchiseId,
            @Valid @RequestBody CreateBranchRequest request) {
        return service.addBranch(franchiseId, request);
    }

    @PostMapping("/{franchiseId}/branches/{branchId}/products")
    public Mono<Franchise> addProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @Valid @RequestBody CreateProductRequest request) {
        return service.addProduct(franchiseId, branchId, request);
    }

    @PutMapping("/{franchiseId}/branches/{branchId}/products/{productId}/stock")
    public Mono<Franchise> updateStock(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @Valid @RequestBody UpdateStockRequest request) {

        return service.updateProductStock(franchiseId, branchId, productId, request);
    }

    @DeleteMapping("/{franchiseId}/branches/{branchId}/products/{productId}")
    public Mono<Franchise> deleteProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId) {

        return service.deleteProduct(franchiseId, branchId, productId);
    }

    @GetMapping("/{franchiseId}/top-stock")
    public Mono<List<TopStockResponse>> getTopStock(
            @PathVariable String franchiseId) {

        return service.getTopStockProducts(franchiseId);
    }
}
