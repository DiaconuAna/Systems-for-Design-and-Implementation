package ro.ubb.socket.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import ro.ubb.socket.domain.Movie;
import ro.ubb.socket.domain.validators.Validator;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.domain.Genre;
import ro.ubb.socket.domain.validators.ValidatorException;

import java.util.Optional;

public class GenreRepositoryImpl implements Repository<Long, Genre>{

    @Autowired
    private JdbcOperations jdbcOperations;

    private Validator<Genre> validator;

    public GenreRepositoryImpl(Validator<Genre> validator){
        this.validator = validator;
        System.out.println("JDBC IN GENRE REPO: " + jdbcOperations);
    }

    @Override
    public Optional<Genre> findOne(Long aLong) {
        String sql = String.format("SELECT * FROM genre WHERE id = %d", aLong);

        return jdbcOperations.query(sql, (result, i)->{
            Long id = result.getLong("id");
            String name = result.getString("name");
            String descr = result.getString("description");

            return new Genre(id, name, descr);
        }).stream().findFirst();
    }

    @Override
    public Iterable<Genre> findAll() {
        String sql = "SELECT * FROM genre";

        return jdbcOperations.query(sql, (result, i)->{
           Long id = result.getLong("id");
           String name = result.getString("name");
           String descr = result.getString("description");

           return new Genre(id, name, descr);
        });
    }

    @Override
    public Optional<Genre> save(Genre entity) throws ValidatorException {
        String sql = "INSERT INTO genre (id, name, description) VALUES (?, ?, ?)";
        jdbcOperations.update(sql, entity.getId(), entity.getName(), entity.getDescription());

        return Optional.of(entity);
    }

    @Override
    public Optional<Genre> delete(Long aLong) {

        var genreToDelete = this.findOne(aLong);
        var sql = "DELETE FROM genre WHERE id = ?";
        System.out.println("GENRE TO DELETE " + aLong);
        genreToDelete.ifPresent((genre -> jdbcOperations.update("DELETE FROM genre WHERE id = ?", aLong)));
        System.out.println("GENRE DELETED");
        return genreToDelete;
    }

    @Override
    public Optional<Genre> update(Genre entity) throws ValidatorException {
        var updateStatement = String.format("UPDATE genre SET name = ?, description = ? WHERE id = ?");
        jdbcOperations.update(updateStatement, entity.getName(), entity.getDescription(), entity.getName());
        return Optional.of(entity); // to return an optional
    }
}
