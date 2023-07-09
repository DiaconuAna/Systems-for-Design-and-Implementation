package catalog.core.service;


import catalog.core.model.Actor;
import catalog.core.model.validators.ActorValidator;
import catalog.core.model.validators.ValidatorException;
import catalog.core.repository.ActorRepository;
import catalog.core.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository repository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ActorValidator validator;

    private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

    @Override
    public Optional<Actor> addActor(Actor actor) throws ValidatorException {
        logger.trace("addActor - method entered");

        validator.validate(actor);
        var res = Optional.of(repository.save(actor));
        logger.trace("addActor: result -> {}", res);

        return res;
    }

    @Override
    public List<Actor> getAllActors() {
        logger.trace("getAllActors - method entered");
        var res = repository.findAll();
        logger.trace("getAllActors: result -> {}", res);

        return res;
    }

    @Override
    @Transactional
    public Optional<Actor> updateActor(Actor actor) throws ValidatorException {
        logger.trace("updateActor - method entered");
        validator.validate(actor);
        Actor updateActor = repository.findById(actor.getId()).orElseThrow();
        updateActor.setFirstName(actor.getFirstName());
        updateActor.setLastName(actor.getLastName());
        updateActor.setAge(actor.getAge());
        updateActor.setGender(actor.getGender());
        updateActor.setPopularity(actor.getPopularity());

        logger.trace("updateActor: result -> {}", updateActor);

        return Optional.of(updateActor);
    }

    @Override
    public Optional<Actor> deleteActor(Long id) {
        logger.trace("deleteActor - method entered");
        Actor deleteActor = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        logger.trace("deleteActor: result -> {}", deleteActor);

        var contracts = contractRepository.findAll();
        for(var c: contracts){
            if (c.getActorId() == id)
                contractRepository.deleteById(c.getId());
        }
        return Optional.of(deleteActor);

        // todo - delete corresponding movies as well

    }

    @Override
    public Optional<Actor> findActor(Long ID) {
        logger.trace("findActor - method entered");
        var res = repository.findById(ID);
        logger.trace("findActor: result -> {}", res);

        return res;
    }

    @Override
    public List<Actor> getSortedByPopularity() {
        logger.trace("getSortedByPopularity - method entered");
        var res = repository.findByOrderByPopularityAsc();
        logger.trace("getSortedByPopularity: result -> {}", res);

        return res;
    }

    @Override
    public List<Actor> getSortedByAge() {
        logger.trace("getSortedByAge - method entered");
        var res = repository.findAllByOrderByAgeAsc();
        logger.trace("getSortedByAge: result -> {}", res);

        return res;
    }
}

