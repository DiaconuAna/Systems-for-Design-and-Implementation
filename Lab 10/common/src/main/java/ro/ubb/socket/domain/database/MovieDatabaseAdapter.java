package ro.ubb.socket.domain.database;

import ro.ubb.socket.domain.Movie;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class MovieDatabaseAdapter implements DatabaseAdapter<Long, Movie> {
    @Override
    public String getTableName() {
        return "movies";
    }

    @Override
    public void insert(DatabaseConnectionDetails connectionDetails, Movie entity) {

        var insertStatement = String.format("INSERT INTO %s (Id, title, director, genreId, productionCountry) values (?, ?, ?, ?, ?)", getTableName());

        try( var connection = DriverManager.getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password);
             var preparedStatement = connection.prepareStatement(insertStatement)
        ) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getTitle());
            preparedStatement.setString(3, entity.getDirector());
            preparedStatement.setLong(4, entity.getGenre());
            preparedStatement.setString(5, entity.getProductionCountry());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
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
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(DatabaseConnectionDetails connectionDetails, Movie entity) {
        // Id, title, director, genreId, productionCountry
        var statement = String.format("UPDATE %s SET title = ?, director = ?, genreId = ?, productionCountry = ? WHERE Id = ?", getTableName());

        try (
                var connection = DriverManager.getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password);
                var preparedStatement = connection.prepareStatement(statement)) {

            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getDirector());
            preparedStatement.setLong(3, entity.getGenre());
            preparedStatement.setString(4, entity.getProductionCountry());
            preparedStatement.setLong(5, entity.getId());

            preparedStatement.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Movie> getAll(DatabaseConnectionDetails connectionDetails) {
        var entities = new HashSet<Movie>();

        var statement = String.format("SELECT * FROM %s", getTableName());

        try (var resultSet = DriverManager
                .getConnection(connectionDetails.getConnectionString(), connectionDetails.username, connectionDetails.password)
                .prepareStatement(statement).executeQuery()){
            while(resultSet.next()){
                entities.add(getFromResultSet(resultSet));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return entities;
    }

    private Movie getFromResultSet(ResultSet resultSet){

        try{
            // title, director, genreId, productionCountry
            var id = resultSet.getLong("Id");
            var title = resultSet.getString("title");
            var dirName = resultSet.getString("director");
            var genreId = resultSet.getLong("genreId");
            var productionCountry = resultSet.getString("productionCountry");

            return new Movie(title, dirName, productionCountry, genreId, id);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;

    }
}
