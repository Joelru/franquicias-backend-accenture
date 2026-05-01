package com.accenture.franchise.service;

import com.accenture.franchise.dto.CreateBranchRequest;
import com.accenture.franchise.dto.CreateFranchiseRequest;
import com.accenture.franchise.dto.CreateProductRequest;
import com.accenture.franchise.model.Branch;
import com.accenture.franchise.model.Franchise;
import com.accenture.franchise.model.Product;
import com.accenture.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository repository;

    public Mono<Franchise> createFranchise(CreateFranchiseRequest request) {
        Franchise franchise = Franchise.builder()
                .name(request.getName())
                .branches(new ArrayList<>())
                .build();

        return repository.save(franchise);
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
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Branch not found in the franchise" + franchiseId));


                    Product product = Product.builder()
                            .id(java.util.UUID.randomUUID().toString())
                            .name(request.getName())
                            .stock(request.getStock().toString())
                            .build();

                    branch.getProducts().add(product);

                    return repository.save(franchise);
                });

    }
}

