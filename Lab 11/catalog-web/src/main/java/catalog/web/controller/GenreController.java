package catalog.web.controller;

import catalog.core.service.GenreService;
import catalog.web.converter.GenreConverter;
import catalog.web.dto.ActorDto;
import catalog.web.dto.GenreDto;
import catalog.web.dto.GenresDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GenreController {
    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreConverter genreConverter;

    @RequestMapping(value = "/genres")
    GenresDto getAllGenres(){

        logger.trace("getAllGenres - method entered");
        var genres = genreService.getAllGenres();
        System.out.println("GENRES: " + genres);
        GenresDto genresDto = new GenresDto(genreConverter.convertModelsToDtos(genres));
        logger.trace("getAllGenres: result -> {}", genresDto);

        return genresDto;
    }

    @RequestMapping(value = "/genres", method = RequestMethod.POST)
    GenreDto addGenre(@RequestBody GenreDto genreDto){
        logger.trace("addGenre - method entered");
        var genre = genreConverter.convertDtoToModel(genreDto);
        var result = genreService.addGenre(genre);
        var resultModel = genreConverter.convertModelToDto(genre);
        logger.trace("addGenre: result -> {}", resultModel);

        return resultModel;
    }

    @RequestMapping(value = "/genres/{id}", method = RequestMethod.PUT)
    GenreDto updateGenre(@PathVariable Long id, @RequestBody GenreDto genreDto){
        logger.trace("updateGenre - method entered");
        var res = genreConverter.convertModelToDto(
          genreService.updateGenre(genreConverter.convertDtoToModel(genreDto)).get()
        );
        logger.trace("updateGnere: result -> {}", res);

        return res;
    }


    @RequestMapping(value = "/genres/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteGenre(@PathVariable Long id){
        logger.trace("deleteGenre - method entered");
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/genres/{id}")
    GenreDto getGenreById(@PathVariable Long id) throws Exception{
        logger.trace("getGenreById - method entered");
        var genreToGet = genreService.getById(id);
        if(genreToGet.isPresent()){
            logger.trace("getGenreById: result -> {}", genreToGet.get());
            return genreConverter.convertModelToDto(genreToGet.get());
        }
        else {
            logger.error("getGenreById : result -> ERROR NO GENRE");
            throw new Exception("No genre");
        }
    }
}
