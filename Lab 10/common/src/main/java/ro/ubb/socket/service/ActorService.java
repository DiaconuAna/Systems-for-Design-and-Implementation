package ro.ubb.socket.service;

import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.repository.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface ActorService {

    CompletableFuture<Optional<Actor>> addActor(Actor actor) throws ValidatorException;

    CompletableFuture<Set<Actor>> getAllActors();

    CompletableFuture<Optional<Actor>> updateActor(Actor actor) throws ValidatorException;

    CompletableFuture<Optional<Actor>> deleteActor(Long id);

    CompletableFuture<Actor> findActor(Long ID);

}
