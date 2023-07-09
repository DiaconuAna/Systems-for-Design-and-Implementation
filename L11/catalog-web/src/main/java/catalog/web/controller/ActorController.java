package catalog.web.controller;

import catalog.core.model.Actor;
import catalog.core.service.ActorService;
import catalog.web.converter.ActorConverter;
import catalog.web.dto.ActorDto;
import catalog.web.dto.ActorsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ActorController {

    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @Autowired
    private ActorService actorService;

    @Autowired
    private ActorConverter actorConverter;


    @RequestMapping(value = "/actors")
    ActorsDto getAllActors(){
        logger.trace("Method entered: getAllActors");
        var actors = actorService.getAllActors();
        ActorsDto actorsDto = new ActorsDto(actorConverter.convertModelsToDtos(actors));
        logger.trace("getAllActors: result -> {}", actors);

        return actorsDto;

    }

    // POST is used to send data to a server to create/update a resource.
    @RequestMapping(value = "/actors", method = RequestMethod.POST)
    ActorDto addActor(@RequestBody ActorDto actorDto){
        logger.trace("Method entered: getAllActors");

        var actor = actorConverter.convertDtoToModel(actorDto);
        var result = actorService.addActor(actor);
        var resultModel = actorConverter.convertModelToDto(actor);

        logger.trace("getAllActors: result -> {}", resultModel);

        return resultModel;
    }

    // PUT is used to send data to a server to create/update a resource.
    // unlike a POST request, a PUT request will always produce the same result
    @RequestMapping(value = "/actors/{id}", method = RequestMethod.PUT)
    ActorDto updateActor(@PathVariable Long id, @RequestBody ActorDto actorDto){

        logger.trace("Method entered: updateActor");

        var res = actorConverter.convertModelToDto(
                actorService.updateActor(actorConverter.convertDtoToModel(actorDto)).get()
        );

        logger.trace("updateActor: result -> {}", res);

        return res;
    }

    // The DELETE method deletes the specified resource.
    // ResponseEntity - Extension of HttpEntity that adds an HttpStatus status code.
    // Used in RestTemplate as well as in @Controller methods.
    @RequestMapping(value = "/actors/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteActor(@PathVariable Long id){

        logger.trace("Method entered: deleteActor");

        actorService.deleteActor(id);

        logger.trace("deleteActor: deleted actor with id = {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/{id}")
    ActorDto getActorById(@PathVariable Long id) throws Exception {

        logger.trace("Method entered: getActorById");

        var actorToGet = actorService.findActor(id);
        if(actorToGet.isPresent()) {
            logger.trace("getActorById: result -> {}", actorToGet.get());
            return actorConverter.convertModelToDto(actorToGet.get());
        }
        else {
            logger.error("getActorById: failed to retrieve actor with id = {}", id);
            throw new Exception("No actor");
        }
    }

    @RequestMapping(value = "/actors/sort/popularity")
    ActorsDto getActorsSortedByPopularity(){
        logger.trace("Method entered: getActorsSortedByPopularity");
        System.out.println("\nHEY : " + actorService.getSortedByPopularity());

        var sorted = actorService.getSortedByPopularity();

        var res =  new ActorsDto(actorConverter.convertModelsToDtos(sorted));

        logger.trace("getActorsSortedByPopularity: result -> {}", res);

        return res;
    }


}
