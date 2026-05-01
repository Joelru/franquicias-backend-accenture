package com.accenture.franchise.service;

import com.accenture.franchise.dto.CreateFranchiseRequest;
import com.accenture.franchise.model.Franchise;
import com.accenture.franchise.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository repository;

    public Mono<Franchise> createFranchise(CreateFranchiseRequest request){
        Franchise franchise = Franchise.builder()
                .name(request.getName())
                .branches(new ArrayList<>())
                .build();

        return repository.save(franchise);
    }
}

