package ro.ubb.socket.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.service.ActorService;
import ro.ubb.socket.utils.Factory;

import java.net.SocketException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ActorServiceImpl implements ActorService {

    @Autowired
    ExecutorService executorService;

    @Autowired
    TcpClient tcpClient;

//    public ActorServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
//        this.executorService = executorService;
//        this.tcpClient = tcpClient;
//    }

    @Override
    public CompletableFuture<Optional<Actor>> addActor(Actor actor) throws ValidatorException {
        // send a Message with the task and wait for the server to answer with a Message

        Message message = new Message("add actor", Factory.actorString(actor));
        Message answer = tcpClient.sendAndReceive(message);

        // check for errors

        if(answer.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->Optional.of(actor),
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Actor>> getAllActors() {

        Message answer;

        answer = tcpClient.sendAndReceive(
                new Message("print actors", "")
        );

        //System.out.println("CLIENT ANSWER: " + answer.getBody());
        String[] tokens = answer.getBody().split(";");

        Set<Actor> actors = Arrays.stream(tokens).map(Factory::getActor).collect(Collectors.toSet());

        return CompletableFuture.supplyAsync(
                ()->actors,
                executorService
        );
    }

    @Override
    public CompletableFuture<Optional<Actor>> updateActor(Actor actor) throws ValidatorException {

        Message response = tcpClient.sendAndReceive(
                new Message("update actor", Factory.actorString(actor))
        );

        if(response.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->Optional.of(actor),
                executorService
        );
    }

    @Override
    public CompletableFuture<Optional<Actor>> deleteActor(Long id) {

        // send the message to the server and expect an answer
        Message response = tcpClient.sendAndReceive(
                new Message("delete actor", Long.toString(id))
        );

        // check for errors
        if(response.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->Optional.of(new Actor()),
                executorService
        );
    }

    @Override
    public CompletableFuture<Actor> findActor(Long ID) {
        Message response;

        try{
            response = tcpClient.sendAndReceive(
                    new Message("find actor", ID.toString())
            );
        } catch (Exception e) {
            throw new RuntimeException();
        }

        Actor answer = Factory.getActor(response.getBody());

        System.out.println("CLIENT ACTOR IS: " + answer.toString());

//        String[] tokens = response.getBody().split(";");
//
//        // get all actors
//        Set<Actor> actors = Arrays.stream(tokens).map(Factory::getActor).collect(Collectors.toSet());
//
//        Optional<Actor> foundActor = actors.stream().filter(actor -> actor.getId().equals(ID)).findAny();

        return CompletableFuture.supplyAsync(
                ()->answer,
                executorService
        );
    }
}
