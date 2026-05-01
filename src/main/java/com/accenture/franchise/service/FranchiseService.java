package com.accenture.franchise.service;

import com.accenture.franchise.dto.*;
import com.accenture.franchise.model.Branch;
import com.accenture.franchise.model.Franchise;
import com.accenture.franchise.model.Product;
import com.accenture.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository repository;


    public Mono<Franchise> createFranchise(CreateFranchiseRequest request) {

        return repository.existsByName(request.getName())
                .flatMap(exists -> {
                    if (!exists) {
                        Franchise franchise = Franchise.builder()
                                .name(request.getName())
                                .branches(new ArrayList<>())
                                .build();

                        return repository.save(franchise);
                    } else {
                        return Mono.error(new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Franchise already exists"
                        ));
                    }

                });
    }

    public Mono<Franchise> addBranch(String franchiseId, CreateBranchRequest request) {
        return repository.findById(franchiseId)
                .flatMap(franchise -> {
                    Branch branch = Branch.builder()
                            .id(java.util.UUID.randomUUID().toString())
                            .name(request.getName())
                            .products(new ArrayList<>())
                            .build();

                    franchise.getBranches().add(branch);

                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> addProduct(String franchiseId, String branchId, CreateProductRequest request) {
        return repository.findById(franchiseId)
                .flatMap(franchise -> {
                    Branch branch = findBranch(franchise, branchId);

                    Product product = Product.builder()
                            .id(java.util.UUID.randomUUID().toString())
                            .name(request.getName())
                            .stock(request.getStock())
                            .build();

                    branch.getProducts().add(product);

                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> updateProductStock(
            String franchiseId,
            String branchId,
            String productId,
            UpdateStockRequest request) {

        return repository.findById(franchiseId)
                .flatMap(franchise -> {

                    Branch branch = findBranch(franchise, branchId);

                    Product product = findProduct(branch, productId);

                    product.setStock(request.getStock());

                    return repository.save(franchise);
                });
    }

    public Mono<Franchise> deleteProduct(
            String franchiseId,
            String branchId,
            String productId) {

        return repository.findById(franchiseId)
                .flatMap(franchise -> {

                    Branch branch = findBranch(franchise, branchId);

                    branch.getProducts().removeIf(product ->
                            product.getId().equals(productId));

                    return repository.save(franchise);
                });
    }

    public Mono<List<TopStockResponse>> getTopStockProducts(String franchiseId) {

        return repository.findById(franchiseId)
                .map(franchise ->
                        franchise.getBranches().stream()
                                .map(branch -> {

                                    Product topProduct = branch.getProducts().stream()
                                            .max(Comparator.comparing(Product::getStock))
                                            .orElse(null);

                                    if (topProduct == null) {
                                        return null;
                                    }

                                    return new TopStockResponse(
                                            branch.getName(),
                                            topProduct.getName(),
                                            topProduct.getStock()
                                    );
                                })
                                .filter(Objects::nonNull)
                                .toList()
                );
    }

    private Branch findBranch(Franchise franchise, String branchId) {
        return franchise.getBranches().stream()
                .filter(b -> b.getId().equals(branchId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Branch not found in the franchise"));
    }

    private Product findProduct(Branch branch, String productId) {
        return branch.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}

