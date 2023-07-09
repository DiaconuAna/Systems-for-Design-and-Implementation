package catalog.core.service;

import catalog.core.model.Genre;
import catalog.core.model.validators.ValidatorException;
import catalog.core.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GenreServiceImpl implements GenreService{

    private static final Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

    @Autowired
    GenreRepository repository;

    @Override
    public Optional<Genre> addGenre(Genre genre) throws ValidatorException {
        logger.trace("addGenre - method entered");
        var res = repository.save(genre);
        logger.trace("addGenre: result -> {}", res);

        return Optional.of(res);
    }

    @Override
    public List<Genre> getAllGenres() {
        logger.trace("getAllGenres - method entered");
        var res = repository.findAll();
        logger.trace("getAllGenres: result -> {}", res);

        return res;
    }

    @Override
    public Optional<Genre> getById(Long id) {
        logger.trace("getById - method entered");
        var res = repository.findById(id);
        logger.trace("getById: result -> {}", res);

        return res;
    }

    @Override
    public Optional<Genre> deleteGenre(Long id) {
        logger.trace("deleteGenre - method entered");
        Genre deleteGenre = repository.findById(id).orElseThrow();
        repository.deleteById(id);
        logger.trace("deleteGenre: result -> {}", deleteGenre);

        return Optional.of(deleteGenre);
    }

    @Override
    @Transactional
    public Optional<Genre> updateGenre(Genre genre) throws ValidatorException {
        logger.trace("updateGenre - method entered");
        Genre updateGenre = repository.findById(genre.getId()).orElseThrow();
        updateGenre.setName(genre.getName());
        updateGenre.setDescription(genre.getDescription());
        logger.trace("updateGenre: result -> {}", updateGenre);

        return Optional.of(updateGenre);
    }
}
