package ro.ubb.socket.server.service;

import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.service.ActorService;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ActorServiceImpl implements ActorService {

    private Repository<Long, Actor> repository;

    public ActorServiceImpl(Repository<Long, Actor> repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Optional<Actor>> addActor(Actor actor) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            return repository.save(actor);
        });
    }

    @Override
    public CompletableFuture<Set<Actor>> getAllActors() {
        return CompletableFuture.supplyAsync(() -> {
            Iterable<Actor> actors = repository.findAll();
            return StreamSupport.stream(actors.spliterator(), false).collect(Collectors.toSet());
        });
    }


    @Override
    public CompletableFuture<Optional<Actor>> updateActor(Actor actor) throws ValidatorException {
        return CompletableFuture.supplyAsync(() -> {
            return repository.update(actor);
        });
    }


    @Override
    public CompletableFuture<Optional<Actor>> deleteActor(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            return repository.delete(id);
        });
    }

    @Override
    public CompletableFuture<Actor> findActor(Long ID) {
        System.out.println("HEY");
        return CompletableFuture.supplyAsync(() -> repository.findOne(ID).get());
    }

    public void addMovieToActor(Long actorID, Long MovieID){
        var OptionalActor = repository.findOne(actorID);

        if(OptionalActor.isPresent()){
            var actor = OptionalActor.get();
            var movies = actor.getActorMovies();
            movies.add(MovieID);
            actor.setActorMovies(movies);
            repository.update(actor);
        }
        else
            throw new RuntimeException();
    }
}

