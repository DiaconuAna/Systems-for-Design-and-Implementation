package ro.ubb.socket.service;

import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.Genre;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface GenreService {


    CompletableFuture<Optional<Genre>> addGenre(Genre genre) throws ValidatorException;

    CompletableFuture<Set<Genre>> getAllGenres();

    CompletableFuture<Optional<Genre>> getById(Long id);

    CompletableFuture<Optional<Genre>> deleteGenre(Long id);

    CompletableFuture<Optional<Genre>> updateGenre(Genre genre) throws ValidatorException;

}
