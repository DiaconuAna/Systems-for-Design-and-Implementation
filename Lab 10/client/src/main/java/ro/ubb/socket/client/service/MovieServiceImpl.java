package ro.ubb.socket.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.Movie;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.service.MovieService;
import ro.ubb.socket.utils.Factory;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {

    @Autowired
    ExecutorService executorService;

    @Autowired
    TcpClient tcpClient;

//    public MovieServiceImpl(ExecutorService executorService, TcpClient tcpClient) {
//        this.executorService = executorService;
//        this.tcpClient = tcpClient;
//    }


    @Override
    public CompletableFuture<Optional<Movie>> addMovie(Movie movie) throws ValidatorException {

        Message message = new Message("add movie", Factory.movieString(movie));
        Message answer = tcpClient.sendAndReceive(message);

        // check for erros

        if(answer.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->Optional.of(movie),
                executorService
        );
    }

    @Override
    public CompletableFuture<Optional<Movie>> deleteMovie(Long id) {

        Message response = tcpClient.sendAndReceive(
                new Message("delete movie", Long.toString(id))
        );

        // check for erros

        if(response.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->Optional.of(new Movie()),
                executorService
        );
    }

    @Override
    public CompletableFuture<Optional<Movie>> updateMovie(Movie m) {

        Message response = tcpClient.sendAndReceive(
                new Message("update movie", Factory.movieString(m))
        );

        if(response.getHeader().equals("error")){
            throw new RuntimeException();
        }

        return CompletableFuture.supplyAsync(
                ()->Optional.of(m),
                executorService
        );

    }

    @Override
    public CompletableFuture<Set<Movie>> getAllMovies() {
        Message answer;

        answer = tcpClient.sendAndReceive(
                new Message("print movies", "")
        );

        String[] tokens = answer.getBody().split(";");

        Set<Movie> movies = Arrays.stream(tokens).map(Factory::getMovie).collect(Collectors.toSet());

        return CompletableFuture.supplyAsync(
                ()->movies,
                executorService
        );
    }

    @Override
    public CompletableFuture<Optional<Movie>> findMovie(Long id) {
        Message response;

        try{
            response = tcpClient.sendAndReceive(
                    new Message("find movie", id.toString())
            );
        } catch (Exception e) {
            throw new RuntimeException();
        }

        Movie answer = Factory.getMovie(response.getBody());

        return CompletableFuture.supplyAsync(
                ()->Optional.of(answer),
                executorService
        );
    }
}
