package catalog.web.controller;

import catalog.core.model.Movie;
import catalog.core.service.MovieService;
import catalog.web.converter.MovieConverter;
import catalog.web.dto.MovieDto;
import catalog.web.dto.MoviesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;

    @RequestMapping(value = "/movies")
    MoviesDto getAllMovies(){
        logger.trace("getAllMovies - method entered");
        var movies = movieService.getAllMovies();
        MoviesDto moviesDto = new MoviesDto(movieConverter.convertModelsToDtos(movies));
        logger.trace("getAllMovies: result -> {}", moviesDto);

        return moviesDto;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    MovieDto addMovie(@RequestBody MovieDto movieDto){
        logger.trace("addMovie - method entered");
        var movie = movieConverter.convertDtoToModel(movieDto);
        movieService.addMovie(movie);
        var resultModel = movieConverter.convertModelToDto(movie);
        logger.trace("addMovie: result -> {}", resultModel);

        return resultModel;
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    MovieDto updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto){
        logger.trace("updateMovie - method entered");
        var res = movieConverter.convertModelToDto(
          movieService.updateMovie(movieConverter.convertDtoToModel(movieDto)).get()
        );
        logger.trace("updateMovie: result -> {}", res);

        return res;
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteMovie(@PathVariable Long id){
        logger.trace("deleteMovie - method entered");
        movieService.deleteMovie(id);
        logger.trace("deleteMovie: result -> OK");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/{id}")
    MovieDto getMovieById(@PathVariable Long id) throws Exception{

        logger.trace("getMovieById - method entered");
        var movieToGet = movieService.findMovie(id);

        if(movieToGet.isPresent()){
            logger.trace("getMovieById: result -> {}", movieToGet.get());
            return movieConverter.convertModelToDto(movieToGet.get());
        }
        else {
            logger.trace("getMovieById: result -> ERROR NO MOVIE");
            throw new Exception("No movie");
        }

    }

    @RequestMapping(value = "/movies/filter/{genreId}")
    MoviesDto filterMoviesByGenre(@PathVariable Long genreId){
        logger.trace("filterMoviesByGenre - method entered");
        var res = new MoviesDto(
          movieConverter.convertModelsToDtos(
                  movieService.filterMoviesByGenre(genreId)
          )
        );
        logger.trace("filterMoviesByGenre: result -> {}", res);

        return res;
    }

}
