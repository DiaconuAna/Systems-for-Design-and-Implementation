package ro.ubb.socket.domain.database;


import ro.ubb.socket.domain.BaseEntity;

import java.sql.SQLException;

public interface DatabaseAdapter<Q, T extends BaseEntity<Q>> {
    String getTableName();
    void insert(DatabaseConnectionDetails connectionDetails, T entity);
    void delete(DatabaseConnectionDetails connectionDetails, Q id);
    void update(DatabaseConnectionDetails connectionDetails, T entity);
    Iterable<T> getAll(DatabaseConnectionDetails connectionDetails);
}
