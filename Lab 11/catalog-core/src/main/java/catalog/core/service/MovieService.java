package catalog.core.service;

import catalog.core.model.Movie;
import catalog.core.model.validators.ValidatorException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface MovieService {
    Optional<Movie> addMovie(Movie movie) throws ValidatorException;

    Optional<Movie> deleteMovie(Long id);

    Optional<Movie> updateMovie(Movie m);

    List<Movie> getAllMovies();

    Optional<Movie> findMovie(Long id);

    List<Movie> filterMoviesByGenre(Long genreId);
}
