package ro.ubb.socket.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Movie extends BaseEntity<Long> {

    private String title;
    private List<Long> actors;
    private String director;
    private Long genre;
    private String productionCountry;

    public Movie(){
        title = "";
        director = "";
        productionCountry = "";
        this.actors = new ArrayList<>();
        this.genre = Long.parseLong("0");
        //this.genre = new Genre();
        setId(Long.parseLong("0"));
    }

    public Movie(String title, String directorName, String productionCountry, Long genre, Long Id){
        this.title = title;
        this.director = directorName;
        this.productionCountry = productionCountry;
        this.actors = new ArrayList<>();
        this.genre = genre;
        setId(Id);
    }

    public Movie(String title, List<Long> actors, String director, Long genre, String productionCountry){
        this.title = title;
        this.actors = actors;
        this.director = director;
        this.genre = genre;
        this.productionCountry = productionCountry;
    }

    public Movie(String title){
        this.title = title;
    }


    public String getTitle(){
        return this.title;
    }

    public List<Long> getActors(){
        return this.actors;
    }

    public String getDirector(){
        return this.director;
    }

    public Long getGenre(){
        return this.genre;
    }

    public String getProductionCountry(){
        return this.productionCountry;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setActors(List<Long> actors) {
        this.actors = actors;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenre(Long genre) {
        this.genre = genre;
    }

    public void setProductionCountry(String productionCountry) {
        this.productionCountry = productionCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return title==movie.title;
    }

    @Override
    public String toString() {
        return "Movie{ ID:  " + getId() +
                " title = " + title + '\'' +
                ", director = " + director +
                ", productionCountry = " + productionCountry +
                ", genre = " + genre +
                '}';
    }
}
