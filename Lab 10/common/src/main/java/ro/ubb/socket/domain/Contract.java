package ro.ubb.socket.domain;

import ro.ubb.socket.utils.Pair;

import java.util.Objects;

public class Contract extends BaseEntity<Long>{

    Long actorId;
    Long movieId;

    public Contract(){

    }

    public Contract(Long Id, Long actor, Long movie){
        setId(Id);
        this.actorId = actor;
        this.movieId = movie;
    }

    public Contract(Long actor, Long movie){
        this.actorId = actor;
        this.movieId = movie;
    }

    public Long getActorId(){
        return this.actorId;
    }

    public Long getMovieId(){
        return this.movieId;
    }

    public void setActor(Long newId){
        this.actorId = newId;
    }

    public void setMovie(Long newId){
        this.movieId = newId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        return Objects.equals(movieId, contract.movieId) && Objects.equals(actorId, contract.actorId);

    }

    @Override
    public String toString() {
        return "Contract : " + getId()
                + " \nActor ID: " + actorId +
                " \nMovie ID: " + movieId;
    }

}
