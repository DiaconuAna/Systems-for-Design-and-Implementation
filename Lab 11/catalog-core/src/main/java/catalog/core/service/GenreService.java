package catalog.core.service;

import catalog.core.model.Genre;
import catalog.core.model.validators.ValidatorException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface GenreService {

    Optional<Genre> addGenre(Genre genre) throws ValidatorException;

    List<Genre> getAllGenres();

    Optional<Genre> getById(Long id);

    Optional<Genre> deleteGenre(Long id);

    Optional<Genre> updateGenre(Genre genre) throws ValidatorException;
}
