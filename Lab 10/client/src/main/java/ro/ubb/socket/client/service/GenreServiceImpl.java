package ro.ubb.socket.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.domain.Genre;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.service.GenreService;
import ro.ubb.socket.utils.Factory;

import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class GenreServiceImpl implements GenreService {
    @Autowired
    ExecutorService executorService;

    @Autowired
    TcpClient tcpClient;

//    public GenreServiceImpl(ExecutorService executorService, TcpClient tcpClient){
//        this.executorService = executorService;
//        this.tcpClient = tcpClient;
//    }


    @Override
    public CompletableFuture<Optional<Genre>> addGenre(Genre genre) throws ValidatorException {

        Message message = new Message("add genre", Factory.genreString(genre));
        Message answer = tcpClient.sendAndReceive(message);

        // check for errors

        if(answer.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->Optional.of(genre),
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Genre>> getAllGenres() {

        Message answer;

        answer = tcpClient.sendAndReceive(new Message("print genres", ""));

        String[] tokens = answer.getBody().split(";");
        Set<Genre> genres = Arrays.stream(tokens).map(Factory::getGenre).collect(Collectors.toSet());

        return CompletableFuture.supplyAsync(
                ()->genres,
                executorService
        );

    }

    @Override
    public CompletableFuture<Optional<Genre>> getById(Long id) {

        Message response;

        try{
            //System.out.println("GENRE ID IS : " + id.toString());
            response = tcpClient.sendAndReceive(
                    new Message("find genre", id.toString())
            );
        } catch (Exception e) {
            throw new RuntimeException();
        }

        Genre answer = Factory.getGenre(response.getBody());

        return CompletableFuture.supplyAsync(
                ()->Optional.of(answer),
                executorService
        );

    }

    @Override
    public CompletableFuture<Optional<Genre>> deleteGenre(Long id) {

        Message response = tcpClient.sendAndReceive(
                new Message("delete genre", Long.toString(id))
        );

        // check for errors
        if(response.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->Optional.of(new Genre()),
                executorService
        );

    }

    @Override
    public CompletableFuture<Optional<Genre>> updateGenre(Genre genre) throws ValidatorException {

        Message response = tcpClient.sendAndReceive(
                new Message("update genre", Factory.genreString(genre))
        );

        if(response.getHeader().equals("error"))
            throw new RuntimeException();

        return CompletableFuture.supplyAsync(
                ()->Optional.of(genre),
                executorService
        );
    }
}
