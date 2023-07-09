package ro.ubb.socket.server.service;

import ro.ubb.socket.domain.Movie;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.service.MovieService;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieServiceImpl implements MovieService {

    private Repository<Long, Movie> repository;

    public MovieServiceImpl(Repository<Long, Movie> repo){
        this.repository = repo;
    }

    @Override
    public CompletableFuture<Optional<Movie>> addMovie(Movie movie) throws ValidatorException {
        return CompletableFuture.supplyAsync(
                ()->{
                    return repository.save(movie);
                }
        );
    }

    @Override
    public CompletableFuture<Optional<Movie>> deleteMovie(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("DELETE MOVIE IN SERVICE");
            return repository.delete(id);
        });
    }

    @Override
    public CompletableFuture<Optional<Movie>> updateMovie(Movie m) {
        System.out.println("*****: " + m);
        return CompletableFuture.supplyAsync(() -> {
            return repository.update(m);
        });
    }

    @Override
    public CompletableFuture<Set<Movie>> getAllMovies() {
        return CompletableFuture.supplyAsync(
                ()->{
                    Iterable<Movie> movies = repository.findAll();
                    return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toSet());
                }
        );
    }

    @Override
    public CompletableFuture<Optional<Movie>> findMovie(Long id) {
        System.out.println("HEY FROM REPO " + id);
        return CompletableFuture.supplyAsync(()->repository.findOne(id));
    }
}
