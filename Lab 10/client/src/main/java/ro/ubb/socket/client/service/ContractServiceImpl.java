package ro.ubb.socket.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.domain.Contract;
import ro.ubb.socket.service.ContractService;
import ro.ubb.socket.utils.Factory;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class ContractServiceImpl implements ContractService {

    @Autowired
    ExecutorService executorService;

    @Autowired
    TcpClient tcpClient;

//    public ContractServiceImpl(ExecutorService executorService, TcpClient tcpClient){
//        this.executorService = executorService;
//        this.tcpClient = tcpClient;
//    }

    @Override
    public CompletableFuture<Optional<Contract>> addContract(Contract contract) {


        Message message = new Message("add contract", Factory.contractString(contract));

        Message answer = tcpClient.sendAndReceive(message);

        if(answer.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->Optional.of(contract),
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Contract>> getAllContracts() {
        Message answer;

        answer = tcpClient.sendAndReceive(new Message("print contracts", ""));

        String[] tokens = answer.getBody().split(";");

        Set<Contract> contracts = Arrays.stream(tokens).map(Factory::getContract).collect(Collectors.toSet());

        return CompletableFuture.supplyAsync(
                ()->contracts,
                executorService
        );
    }

    @Override
    public CompletableFuture<Optional<Contract>> findContract(Long Id) {
        return null;
    }
}
