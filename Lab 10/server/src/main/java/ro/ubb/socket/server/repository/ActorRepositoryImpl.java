package ro.ubb.socket.server.repository;
//

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.domain.BaseEntity;
import ro.ubb.socket.domain.validators.Validator;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.server.config.JdbcConfig;

import javax.inject.Inject;
import java.lang.management.OperatingSystemMXBean;
import java.util.Optional;


public class ActorRepositoryImpl implements Repository<Long, Actor>{

    //@SuppressWarnings("SpringJavaAutowiringInspection")
    //@Inject
    @Autowired
    private JdbcOperations jdbcOperations;

    private Validator<Actor> validator;

    //  public ActorRepositoryImpl(Validator<Actor> validator, JdbcOperations jd)
    public ActorRepositoryImpl(Validator<Actor> validator){
        //jdbcOperations = jd;
        this.validator = validator;
        //System.out.println("JDBC IN ACTOR REPO: " + jdbcOperations);

    }

    @Override
    public Optional<Actor> findOne(Long aLong) {
        String sql = String.format("SELECT * FROM actors WHERE id = %d", aLong);

        return jdbcOperations.query(sql, (result ,i) -> {

            Long id = result.getLong("Id");
            String firstName = result.getString("FirstName");
            String lastName = result.getString("LastName");
            int age = result.getInt("Age");
            int popularity = result.getInt("Popularity");
            String gender = result.getString("Gender");

            return new Actor(id, firstName, lastName, age, popularity, gender);
        }).stream().findFirst();

    }

    @Override
    public Iterable<Actor> findAll() {
        String sql = "SELECT * FROM actors";

        return jdbcOperations.query(sql, (result ,i) -> {
           Long id = result.getLong("Id");
           String firstName = result.getString("FirstName");
           String lastName = result.getString("LastName");
           int age = result.getInt("Age");
           int popularity = result.getInt("Popularity");
           String gender = result.getString("Gender");

           return new Actor(id, firstName, lastName, age, popularity, gender);
       });
    }

    @Override
    public Optional<Actor> save(Actor entity) throws ValidatorException {
        String insertStatement = String.format("INSERT INTO %s (Id, FirstName, LastName, Age, Popularity, Gender) values (?, ?, ?, ?, ?, ?)", "actors");
        jdbcOperations.update(insertStatement, entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getAge(), entity.getPopularity(), entity.getGender());

        return Optional.of(entity);}

    @Override
    public Optional<Actor> delete(Long aLong) {
        var actorToDelete = this.findOne(aLong);
        System.out.println("Actor to be deleted is: " + actorToDelete);
        actorToDelete.ifPresent((actor -> jdbcOperations.update("DELETE FROM actors WHERE Id = ?", actor.getId())));
        return actorToDelete;
    }

    @Override
    public Optional<Actor> update(Actor entity) throws ValidatorException {
        var statement = String.format("UPDATE %s SET FirstName = ?, LastName = ?, Age = ?, Popularity = ?, Gender = ? WHERE Id = ?", "actors");
        jdbcOperations.update(statement, entity.getFirstName(), entity.getLastName(), entity.getAge(), entity.getPopularity(), entity.getGender(), entity.getId());
        return this.findOne(entity.getId());
        }

}
