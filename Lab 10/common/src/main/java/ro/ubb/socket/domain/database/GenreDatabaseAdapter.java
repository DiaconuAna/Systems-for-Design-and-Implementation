package ro.ubb.socket.domain.database;

import ro.ubb.socket.domain.Genre;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class GenreDatabaseAdapter implements DatabaseAdapter<Long, Genre> {
    @Override
    public String getTableName() {
        return "Genre";
    }

    @Override
    public void insert(DatabaseConnectionDetails connectionDetails, Genre entity) {
        var statement = String.format("INSERT INTO %s (Id, Name, Description) values (?, ?, ?)", getTableName());
        try(
                var connection = DriverManager.getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password);
                var preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(DatabaseConnectionDetails connectionDetails, Long id) {
        var statement = String.format("DELETE FROM %s WHERE Id = ?", getTableName());

        try (
                var connection = DriverManager.getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password);
                var preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DatabaseConnectionDetails connectionDetails, Genre entity) {
        var statement = String.format("UPDATE %s SET Name = ?, Description = ? WHERE Id = ?", getTableName());

        try (
                var connection = DriverManager.getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password);
                var preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setLong(3, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Genre> getAll(DatabaseConnectionDetails connectionDetails) {
        var entities = new HashSet<Genre>();
        var statement = String.format("SELECT * FROM %s", getTableName());

        try (var resultSet = DriverManager
                .getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password)
                .prepareStatement(statement).executeQuery()) {

            while(resultSet.next()) {
                entities.add(getFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;
    }

    private Genre getFromResultSet(ResultSet resultSet) {
        try {
            var id = resultSet.getLong("Id");
            var name = resultSet.getString("Name");
            var description = resultSet.getString("Description");

            return new Genre(id, name, description);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
