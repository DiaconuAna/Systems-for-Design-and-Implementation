package catalog.core.service;

import catalog.core.model.Contract;
import catalog.core.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService{

    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Autowired
    ContractRepository repository;

    @Override
    public Optional<Contract> addContract(Contract contract) {
        logger.trace("addContract - method entered");
        var res = repository.save(contract);
        logger.trace("addContract: result -> {}", res);

        return Optional.of(res);
    }

    @Override
    public List<Contract> getAllContracts() {
        logger.trace("getAllContracts - method entered");
        var res = repository.findAll();
        logger.trace("getAllContracts: result -> {}", res);

        return res;
    }

    @Override
    public Optional<Contract> findContract(Long Id) {
        logger.trace("findContract - method entered");
        var res = repository.findById(Id);
        logger.trace("findContract: result -> {}", res);

        return res;
    }

    @Override
    public Optional<Contract> deleteContract(Long id) {
        logger.trace("deleteContract - method entered");
        Contract deleteContract = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        logger.trace("deleteContract: result -> {}", deleteContract);
        return Optional.of(deleteContract);
    }
}
