package catalog.core.repository;

import catalog.core.model.Actor;
import catalog.core.model.Movie;

import java.util.List;

public interface MovieRepository extends CatalogRepository<Movie, Long>{
    List<Movie> findByGenreId(Long genreId);
}
