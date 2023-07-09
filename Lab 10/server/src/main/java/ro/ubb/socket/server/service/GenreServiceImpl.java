package ro.ubb.socket.server.service;

import ro.ubb.socket.domain.Genre;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.service.GenreService;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GenreServiceImpl implements GenreService {

    public Repository<Long, Genre> repository;

    public GenreServiceImpl(Repository<Long, Genre> repo){
        this.repository = repo;
    }

    @Override
    public CompletableFuture<Optional<Genre>> addGenre(Genre genre) throws ValidatorException {
        return CompletableFuture.supplyAsync(
                ()->{
                    return repository.save(genre);
                }
        );

    }

    @Override
    public CompletableFuture<Set<Genre>> getAllGenres() {
        return CompletableFuture.supplyAsync(
                () -> {
                    Iterable<Genre> genres = repository.findAll();
                    return StreamSupport.stream(genres.spliterator(), false).collect(Collectors.toSet());
                }
        );
    }

    @Override
    public CompletableFuture<Optional<Genre>> getById(Long id) {
        //System.out.println("ID IS " + id);
        return CompletableFuture.supplyAsync(()->repository.findOne(id));
    }

    @Override
    public CompletableFuture<Optional<Genre>> deleteGenre(Long id) {

        return CompletableFuture.supplyAsync(
                () -> {
                    return repository.delete(id);
                }
        );
    }

    @Override
    public CompletableFuture<Optional<Genre>> updateGenre(Genre genre) throws ValidatorException {
        return CompletableFuture.supplyAsync(
                ()->{
                    return repository.update(genre);
                }
        );
    }
}
