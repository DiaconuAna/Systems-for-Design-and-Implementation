package ro.ubb.socket.domain.database;


import ro.ubb.socket.domain.Actor;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class ActorDatabaseAdapter implements DatabaseAdapter<Long, Actor>{

    @Override
    public String getTableName() {
        return "Actors";
    }

    @Override
    public void insert(DatabaseConnectionDetails connectionDetails, Actor entity) {
        var insertStatement = String.format("INSERT INTO %s (Id, FirstName, LastName, Age, Popularity, Gender) values (?, ?, ?, ?, ?, ? )", getTableName());
        try(var connection = DriverManager.getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password);
            var preparedStatement = connection.prepareStatement(insertStatement)){
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setInt(4, entity.getAge());
            preparedStatement.setInt(5, entity.getPopularity());
            preparedStatement.setString(6, entity.getGender());

            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(DatabaseConnectionDetails connectionDetails, Long id){
        var statement = String.format("DELETE FROM %s WHERE Id = ?", getTableName());

        try(
                var connection = DriverManager.getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password);
                var preparedStatement = connection.prepareStatement(statement)
        ){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(DatabaseConnectionDetails connectionDetails, Actor entity) {
        var statement = String.format("UPDATE %s SET FirstName = ?, LastName = ?, Age = ?, Popularity = ?, Gender = ? WHERE Id = ?", getTableName());

        try (
                var connection = DriverManager.getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password);
                var preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setInt(4, entity.getPopularity());
            preparedStatement.setString(5, entity.getGender());
            preparedStatement.setLong(6, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Actor> getAll(DatabaseConnectionDetails connectionDetails) {
        var entities = new HashSet<Actor>();
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

    private Actor getFromResultSet(ResultSet resultSet) {
        try {
            var id = resultSet.getLong("Id");
            var FirstName = resultSet.getString("FirstName");
            var LastName = resultSet.getString("LastName");
            // age, popularity, gender
            var Age = resultSet.getInt("Age");
            var Popularity = resultSet.getInt("Popularity");
            var Gender = resultSet.getString("Gender");

            return new Actor(id, FirstName, LastName, Age, Popularity, Gender);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
