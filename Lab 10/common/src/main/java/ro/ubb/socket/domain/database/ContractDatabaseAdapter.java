package ro.ubb.socket.domain.database;

import ro.ubb.socket.domain.Contract;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class ContractDatabaseAdapter implements DatabaseAdapter<Long, Contract> {
    @Override
    public String getTableName() {
        return "contract";
    }

    @Override
    public void insert(DatabaseConnectionDetails connectionDetails, Contract entity) {
        var insertStatement = String.format("INSERT INTO %s (Id, MovieID, ActorID) values (?, ?, ?)", getTableName());

        try (var connection = DriverManager.getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password);
             var preparedStatement = connection.prepareStatement(insertStatement)) {

            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setLong(2, entity.getMovieId());
            preparedStatement.setLong(3, entity.getActorId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(DatabaseConnectionDetails connectionDetails, Long id) {

    }

    @Override
    public void update(DatabaseConnectionDetails connectionDetails, Contract entity) {

    }

    @Override
    public Iterable<Contract> getAll(DatabaseConnectionDetails connectionDetails) {
        var entities = new HashSet<Contract>();

        var statement = String.format("SELECT * FROM %s", getTableName());

        try(var resultSet = DriverManager
                .getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password)
                .prepareStatement(statement).executeQuery()){

            while(resultSet.next()) {
                entities.add(getFromResultSet(resultSet));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;

    }

    private Contract getFromResultSet(ResultSet resultSet){
        try{
            var id = resultSet.getLong("Id");
            var movieId = resultSet.getLong("MovieID");
            var actorId = resultSet.getLong("ActorID");

            return new Contract(id, actorId, movieId);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
