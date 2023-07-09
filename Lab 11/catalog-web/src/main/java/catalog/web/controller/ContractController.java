package catalog.web.controller;

import catalog.core.model.Contract;
import catalog.core.model.Movie;
import catalog.core.model.MovieActor;
import catalog.core.service.ActorService;
import catalog.core.service.ContractService;
import catalog.core.service.GenreService;
import catalog.core.service.MovieService;
import catalog.web.converter.ContractConverter;
import catalog.web.converter.GenreConverter;
import catalog.web.converter.MovieActorConverter;
import catalog.web.dto.ContractDto;
import catalog.web.dto.ContractsDto;
import catalog.web.dto.MovieActorsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ContractController {
    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @Autowired
    private ContractService contractService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ContractConverter contractConverter;

    @Autowired
    private MovieActorConverter movieActorConverter;

    @RequestMapping(value = "/contracts")
    ContractsDto getAllContracts(){
        logger.trace("getAllContracts - method entered");
        var contracts = contractService.getAllContracts();
        ContractsDto contractsDto = new ContractsDto(contractConverter.convertModelsToDtos(contracts));
        logger.trace("getAllContracts: result -> {}", contractsDto);

        return contractsDto;
    }

    @RequestMapping(value = "/contracts", method = RequestMethod.POST)
    ContractDto addContract(@RequestBody ContractDto contractDto){
        logger.trace("addContract - method entered");
        var contract = contractConverter.convertDtoToModel(contractDto);
        var result = contractService.addContract(contract);
        var resultModel = contractConverter.convertModelToDto(contract);
        logger.trace("addContract: result -> {}", resultModel);

        return resultModel;
    }

    @RequestMapping(value = "/contracts/{id}")
    ContractDto getContractById(@PathVariable Long id) throws Exception{
        logger.trace("getContractById - method entered");
        var contractToGet = contractService.findContract(id);

        if(contractToGet.isPresent()){
            logger.trace("getContractById: result -> {}", contractToGet.get());
            return contractConverter.convertModelToDto(contractToGet.get());
        }
        else {
            logger.error("getContractById: result -> ERROR - NO CONTRACT");
            throw new Exception("No contract");
        }
    }

    @RequestMapping(value = "/contracts/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteContract(@PathVariable Long id){
        logger.trace("deleteContract - method entered");
        contractService.deleteContract(id);
        logger.trace("deleteContract: result -> OK");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "contracts/getAggregate")
    MovieActorsDto aggregateMovieActors(){
        var movies = contractService.getAllContracts().stream().map(Contract::getMovieId).collect(Collectors.toSet());
        List<MovieActor> result = new ArrayList<MovieActor>();
        System.out.println("MOVIES -> " + movies);
        for(var m: movies){
            var foundMovie = movieService.findMovie(m);
            System.out.println("MOVIE : " + foundMovie);
            var entity = new MovieActor();
            System.out.println("ENTITY : " + entity);
            entity.setMovieTitle(foundMovie.get().getTitle());

            var actors = contractService.getAllContracts().stream().filter(c -> c.getMovieId() == m)
                                    .map(c -> c.getActorId()).collect(Collectors.toList());

            if(!actors.isEmpty()) {
                for (var a : actors) {
                    var foundActor = actorService.findActor(a);
                    var provisionalActors = entity.getActors();
                    provisionalActors.add(foundActor.get().getFirstName() + " " + foundActor.get().getLastName());
                    entity.setActors(provisionalActors);
                }
                System.out.println("ENTITY -> " + entity);
            }
            result.add(entity);
        }
        System.out.println(result);
        // convert result to dto
        // ContractsDto contractsDto = new ContractsDto(contractConverter.convertModelsToDtos(contracts));
        var ret = new MovieActorsDto(movieActorConverter.convertModelsToDtos(result));
        System.out.println(ret);

        return ret;
    }
    // filter with movieId - movieName and Actor
    // create a new dto
}
