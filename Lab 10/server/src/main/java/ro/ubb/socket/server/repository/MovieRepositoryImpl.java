package ro.ubb.socket.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import ro.ubb.socket.domain.Movie;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.domain.validators.Validator;
import ro.ubb.socket.domain.validators.ValidatorException;

import java.util.Optional;

public class MovieRepositoryImpl implements Repository<Long, Movie>{

    @Autowired
    private JdbcOperations jdbcOperations;

    private Validator<Movie> validator;

    public MovieRepositoryImpl(Validator<Movie> validator){
        this.validator = validator;
        System.out.println("JDBC IN MOVIE REPO: " + jdbcOperations);
    }

    @Override
    public Optional<Movie> findOne(Long aLong) {
        String sql = String.format("SELECT * FROM movies WHERE id = %d", aLong);

        return jdbcOperations.query(sql, (result, i)->{

            Long id = result.getLong("id");
            String title = result.getString("title");
            String director = result.getString("director");
            Long genreId = result.getLong("genreId");
            String country = result.getString("productionCountry");

            return new Movie(title, director, country, genreId, id);
        }).stream().findFirst();
    }

    @Override
    public Iterable<Movie> findAll() {
        String sql = "SELECT * FROM movies";

        return jdbcOperations.query(sql, (result, i)->{
           Long id =  result.getLong("id");
           String title = result.getString("title");
           String director = result.getString("director");
           Long genreId = result.getLong("genreId");
           String country = result.getString("productionCountry");

           return new Movie(title, director, country, genreId, id);
        });

    }

    @Override
    public Optional<Movie> save(Movie entity) throws ValidatorException {
        System.out.println("hey from movie impl " + entity);
        String insertStatement = "INSERT INTO movies (id, title, director, genreId, productionCountry) values (?, ?, ?, ?, ?)";
        jdbcOperations.update(insertStatement, entity.getId(), entity.getTitle(), entity.getDirector(), entity.getGenre(), entity.getProductionCountry());
        System.out.println("Movie " + entity + " inserted!");
        return Optional.of(entity);
    }

    @Override
    public Optional<Movie> delete(Long aLong) {
        System.out.println("Movie delete in repo");
        //var movieToDelete = this.findOne(aLong);
        //System.out.println("Movie to delete " + movieToDelete);
        //movieToDelete.ifPresent((movie ->
        jdbcOperations.update("DELETE FROM movies WHERE id = ?", aLong);
        System.out.println("Movie deleted");
        return Optional.of(new Movie());
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ValidatorException {
        var updateStatement = String.format("UPDATE %s SET title = ?, director = ?, genreId = ?, productionCountry = ? WHERE id = ?", "movies");
        jdbcOperations.update(updateStatement, entity.getTitle(), entity.getDirector(), entity.getGenre(), entity.getProductionCountry(), entity.getId());
        return this.findOne(entity.getId());
    }
}
