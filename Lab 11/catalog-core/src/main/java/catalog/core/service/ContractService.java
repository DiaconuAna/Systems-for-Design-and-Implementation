package catalog.core.service;

import catalog.core.model.Contract;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface ContractService {

    Optional<Contract> addContract(Contract contract);

    List<Contract> getAllContracts();

    Optional<Contract> findContract(Long Id);

    Optional<Contract> deleteContract(Long id);
}
