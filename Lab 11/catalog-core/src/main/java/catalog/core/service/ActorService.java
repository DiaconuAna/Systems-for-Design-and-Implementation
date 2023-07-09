package catalog.core.service;


import catalog.core.model.Actor;
import catalog.core.model.validators.ValidatorException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface ActorService {

    Optional<Actor> addActor(Actor actor) throws ValidatorException;

    List<Actor> getAllActors();

    Optional<Actor> updateActor(Actor actor) throws ValidatorException;

    Optional<Actor> deleteActor(Long id);

    Optional<Actor> findActor(Long ID);

    List<Actor> getSortedByPopularity();

    List<Actor> getSortedByAge();
}
