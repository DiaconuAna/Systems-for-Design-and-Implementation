package ro.ubb.socket.server.service;

import ro.ubb.socket.domain.Contract;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.service.ContractService;

import java.awt.*;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ContractServiceImpl implements ContractService {

    public Repository<Long, Contract> repository;

    public ContractServiceImpl(Repository<Long, Contract> repo){
        this.repository = repo;
    }


    @Override
    public CompletableFuture<Optional<Contract>> addContract(Contract contract) {
        return CompletableFuture.supplyAsync(
                ()->{
                    return repository.save(contract);
                }
        );
    }

    @Override
    public CompletableFuture<Set<Contract>> getAllContracts() {
        return CompletableFuture.supplyAsync(
                ()->{
                    Iterable<Contract> contracts = repository.findAll();
                    return StreamSupport.stream(contracts.spliterator(), false).collect(Collectors.toSet());
                }
        );
    }

    @Override
    public CompletableFuture<Optional<Contract>> findContract(Long Id) {
        return CompletableFuture.supplyAsync(
                ()->repository.findOne(Id)
        );
    }
}
