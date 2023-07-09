package ro.ubb.socket.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import ro.ubb.socket.domain.Contract;
import ro.ubb.socket.domain.Movie;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.domain.validators.Validator;
import ro.ubb.socket.domain.validators.ValidatorException;

import java.util.Optional;

public class ContractRepositoryImpl implements Repository<Long, Contract>{

    @Autowired
    private JdbcOperations jdbcOperations;

    private Validator<Contract> validator;

    public ContractRepositoryImpl(Validator<Contract> validator){
        this.validator = validator;
        System.out.println("JDBC IN CONTRACT REPO: " + jdbcOperations);
    }

    @Override
    public Optional<Contract> findOne(Long aLong) {
        String sql = "SELECT * FROM contract";

        return jdbcOperations.query(sql, (result, i)->{
            Long id = result.getLong("Id");
            Long movieId = result.getLong("MovieID");
            Long actorId = result.getLong("ActorID");

            return new Contract(id, actorId, movieId);
        }).stream().findFirst();
    }

    @Override
    public Iterable<Contract> findAll() {
        String sql = "SELECT * FROM contract";

        return jdbcOperations.query(sql, (result, i)->{
           Long id = result.getLong("Id");
           Long movieId = result.getLong("MovieID");
           Long actorId = result.getLong("ActorID");

           return new Contract(id, actorId, movieId);
        });
    }

    @Override
    public Optional<Contract> save(Contract entity) throws ValidatorException {
        String sql = String.format("INSERT INTO contract (Id, MovieID, ActorID) VALUES (?, ?, ?)");
        jdbcOperations.update(sql, entity.getId(), entity.getMovieId(), entity.getActorId());
        return Optional.of(entity);
    }

    @Override
    public Optional<Contract> delete(Long aLong) {
        var contractToDelete = this.findOne(aLong);
        contractToDelete.ifPresent((contract -> jdbcOperations.update("DELETE FROM contract WHERE Id = ?")));
        return contractToDelete;
    }

    @Override
    public Optional<Contract> update(Contract entity) throws ValidatorException {
        var updateStatement = String.format("UPDATE contract SET ActorId =?, MovieId = ? WHERE Id = ?");
        jdbcOperations.update(updateStatement, entity.getActorId(), entity.getMovieId(), entity.getId());
        return Optional.of(entity);
    }
}
