package ro.ubb.socket.service;

import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.Movie;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.repository.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface MovieService {

    CompletableFuture<Optional<Movie>> addMovie(Movie movie) throws ValidatorException;

    CompletableFuture<Optional<Movie>> deleteMovie(Long id);

    CompletableFuture<Optional<Movie>> updateMovie(Movie m);

    CompletableFuture<Set<Movie>> getAllMovies();

    CompletableFuture<Optional<Movie>>findMovie(Long id);
}
