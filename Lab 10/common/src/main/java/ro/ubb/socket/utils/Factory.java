package ro.ubb.socket.utils;

import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.Contract;
import ro.ubb.socket.domain.Genre;
import ro.ubb.socket.domain.Movie;

public class Factory {

    public static String contractString(Contract contract){
        return contract.getId() + "," + contract.getActorId() + "," + contract.getMovieId();
    }

    public static Contract getContract(String message){
        if(message.length() == 0)
            return new Contract();
        else{
            String[] tokens = message.split(",");

            if(tokens.length == 0){
                return new Contract();
            }
            else{
                var id = Long.parseLong(tokens[0]);
                var actorId = Long.parseLong(tokens[1]);
                var movieId = Long.parseLong(tokens[2]);

                return new Contract(id, actorId, movieId);
            }
        }
    }

    public static String movieString(Movie movie){

        //Genre movieGenre = movie.getGenre();
        //String g = genreString(movieGenre);

        return movie.getId() + "," + movie.getTitle() + "," + movie.getGenre() + "," + movie.getDirector() + "," + movie.getProductionCountry();
    }

    public static Movie getMovie(String message){
        if(message.length() == 0)
            return new Movie();
        else{
            String[] tokens = message.split(",");


            if(tokens.length == 0)
                return new Movie();
            else{
                var id = Long.parseLong(tokens[0]);
                var title = tokens[1];
                var genreId = Long.parseLong(tokens[2]);
                //Genre genre = getGenre(tokens[2] +"," +tokens[3] +","+ tokens[4]);
                //System.out.println("MOVIE GENRE IS: " + genre);
                var dirName = tokens[3];
                var productionCountry = tokens[4];

                return new Movie(title, dirName, productionCountry, genreId, id);
            }
        }
    }

    public static String genreString(Genre genre){
        return genre.getId() + "," + genre.getName() + "," + genre.getDescription();
    }

    public static Genre getGenre(String message) {
        if(message.length() == 0)
            return new Genre();
        else{
            String[] tokens = message.split(",");

            if(tokens.length == 0)
                return new Genre();
            else{
                var id = Long.parseLong(tokens[0]);
                var name = tokens[1];
                var description = tokens[2];

                return new Genre(id, name, description);

            }
        }

    }

    public static String actorString(Actor actor){
        return actor.getId() + "," + actor.getFirstName() + "," + actor.getLastName() + "," + actor.getGender() + "," + actor.getAge() + "," + actor.getPopularity();
    }

        public static Actor getActor(String message)
    {
        if(message.length() == 0)
        {
            return new Actor();
        }

        String[] tokens = message.split(",");

        if(tokens.length == 0)
        {
            return new Actor();
        }

        var id = Long.parseLong(tokens[0]);
        var firstname = tokens[1];
        var lastname=tokens[2];
        var gender = tokens[3];
        var age = Integer.parseInt(tokens[4]);
        var popularity = Integer.parseInt(tokens[5]);

        return new Actor(id, firstname, lastname, age, popularity, gender);

    }


}
