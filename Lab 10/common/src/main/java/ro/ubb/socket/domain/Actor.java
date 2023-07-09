package ro.ubb.socket.domain;

import java.util.ArrayList;
import java.util.List;

public class Actor extends BaseEntity<Long>{
    private String firstName, lastName;
    private List<Long> actorMovies;
    private int age;
    private int popularity; //from 0 to 10
    private String gender; //male, female, non-binary

    public Actor(){}

    public Actor(Long id, String firstName, String lastName,int age, int p, String gender){
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.popularity = p;
        this.gender = gender;
        this.actorMovies = new ArrayList<>();
        //System.out.println(this.getId());
    }

    public Actor(String firstName, String lastName,int age, int p, String gender){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.popularity = p;
        this.gender = gender;
        this.actorMovies = new ArrayList<>();
        //System.out.println(this.getId());
    }

    public Actor(String firstName, String lastName, List<Long> movies, int age, int p, String gender){
        this.firstName = firstName;
        this.lastName = lastName;
        this.actorMovies = movies;
        this.age = age;
        this.popularity = p;
        this.gender = gender;
        //System.out.println(this.getId());
    }

    public String getFirstName(){ return this.firstName; }
    public void setFirstName(String fn){ this.firstName = fn;}

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String ln){
        this.lastName = ln;
    }

    public String getGender(){
        return this.gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public int getAge(){
        return this.age;
    }

    public void setAge(int a){
        this.age = a;
    }

    public int getPopularity(){
        return this.popularity;
    }

    public void setPopularity(int p){
        this.popularity = p;
    }

    public List<Long> getActorMovies(){
        return this.actorMovies;
    }

    public void setActorMovies(List<Long> l){
        this.actorMovies = l;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actor actor = (Actor) o;

        if(age != actor.age) return false;
        if(popularity != actor.popularity) return false;
        if(!firstName.equals(actor.firstName)) return false;
        if(!lastName.equals(actor.lastName)) return false;

        return(gender.equals(actor.gender));
    }

    @Override
    public String toString() {
        return "Actor{ ID: " + this.getId().toString() +
        " First Name: " + firstName +", Last Name: "
        + lastName + ", Gender: " + gender + ", Age: "
        + Integer.toString(age) + "}";
    }
}
