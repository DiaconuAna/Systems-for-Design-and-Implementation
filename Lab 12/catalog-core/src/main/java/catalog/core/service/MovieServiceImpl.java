package catalog.core.service;

import catalog.core.model.Movie;
import catalog.core.model.validators.MovieValidator;
import catalog.core.model.validators.ValidatorException;
import catalog.core.repository.ContractRepository;
import catalog.core.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService{

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    MovieValidator validator;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    MovieRepository repository;

    @Override
    public Optional<Movie> addMovie(Movie movie) throws ValidatorException {
        logger.trace("addMovie - method entered");
        validator.validate(movie);
        var res = repository.save(movie);
        logger.trace("addMovie: result -> {}", res);

        return Optional.of(res);
    }

    @Override
    public Optional<Movie> deleteMovie(Long id) {
        logger.trace("deleteMovie - method entered");
        Movie deleteMovie = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        logger.trace("deleteMovie: result -> {}", deleteMovie);

        var contracts = contractRepository.findAll();
        for(var c: contracts){
            if (c.getMovieId() == id)
                contractRepository.deleteById(c.getId());
        }

        return Optional.of(deleteMovie);
    }

    @Override
    @Transactional
    public Optional<Movie> updateMovie(Movie m) {
        logger.trace("updateMovie - method entered");
        validator.validate(m);
        Movie updateMovie = repository.findById(m.getId()).orElseThrow();
        updateMovie.setTitle(m.getTitle());
        updateMovie.setDirector(m.getDirector());
        updateMovie.setGenreId(m.getGenreId());
        updateMovie.setProductionCountry(m.getProductionCountry());
        logger.trace("updateMovie: result -> {}", updateMovie);

        return Optional.of(updateMovie);
    }

    @Override
    public List<Movie> getAllMovies() {
        logger.trace("getAllMovies - method entered");
        var res = repository.findAll();
        logger.trace("getAllMovies: result -> {}", res);

        return res;
    }

    @Override
    public Optional<Movie> findMovie(Long id) {
        logger.trace("findMovie - method entered");
        var res = repository.findById(id);
        logger.trace("findMovie: result -> {}", res);

        return res;
    }


    @Override
    public List<Movie> filterMoviesByGenre(Long genreId) {
        logger.trace("filterMoviesByGenre - method entered; genreId = {}", genreId);
        List<Movie> result = repository.findByGenreId(genreId);
        System.out.println("FILTER: " + result);
        logger.trace("filterMoviesByGenre - method finished; result = {}", result);
        return result;
    }
}
