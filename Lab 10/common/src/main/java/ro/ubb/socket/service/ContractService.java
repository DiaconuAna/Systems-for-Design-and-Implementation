package ro.ubb.socket.service;

import ro.ubb.socket.domain.Contract;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface ContractService {

    CompletableFuture<Optional<Contract>> addContract(Contract contract);

    CompletableFuture<Set<Contract>> getAllContracts();

    CompletableFuture<Optional<Contract>> findContract(Long Id);
}
