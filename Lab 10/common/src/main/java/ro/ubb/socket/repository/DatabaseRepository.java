package ro.ubb.socket.repository;


import ro.ubb.socket.domain.BaseEntity;
import ro.ubb.socket.domain.database.DatabaseAdapter;
import ro.ubb.socket.domain.database.DatabaseConnectionDetails;
import ro.ubb.socket.domain.validators.Validator;
import ro.ubb.socket.domain.validators.ValidatorException;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class DatabaseRepository<ID,T extends BaseEntity<ID>> extends InMemoryRepository<ID,T> {
    public final DatabaseAdapter<ID, T> adapter;
    private final DatabaseConnectionDetails connectionDetails;

    public DatabaseRepository(Validator<T> validator, DatabaseConnectionDetails connectionDetails, DatabaseAdapter<ID, T> adapter) {
        super(validator);
        this.connectionDetails = connectionDetails;
        this.adapter = adapter;
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        adapter.insert(connectionDetails, entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<T> delete(ID id) {
        var existing = StreamSupport.stream(adapter.getAll(connectionDetails).spliterator(), false).filter(x -> x.getId() == id).findFirst();

        adapter.delete(connectionDetails, id);

        return existing;
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        adapter.update(connectionDetails, entity);

        return StreamSupport
                .stream(adapter.getAll(connectionDetails).spliterator(), false)
                .filter(x -> x.getId() == entity.getId())
                .findFirst();
    }

    @Override
    public Iterable<T> findAll() {
        return adapter.getAll(connectionDetails);
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        return StreamSupport
                .stream(adapter.getAll(connectionDetails).spliterator(), false)
                .filter(t -> t.getId() == id)
                .findAny();
    }
}
