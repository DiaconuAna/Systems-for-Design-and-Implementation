package ro.ubb.socket.server.tcp;

import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.Contract;
import ro.ubb.socket.domain.Genre;
import ro.ubb.socket.domain.Movie;
import ro.ubb.socket.domain.validators.ValidatorException;
import ro.ubb.socket.server.service.GenreServiceImpl;
import ro.ubb.socket.server.service.MovieServiceImpl;
import ro.ubb.socket.service.ActorService;
import ro.ubb.socket.service.ContractService;
import ro.ubb.socket.service.GenreService;
import ro.ubb.socket.service.MovieService;
import ro.ubb.socket.utils.Factory;

import java.awt.event.MouseWheelEvent;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MessageHandler {
    ActorService actorService;
    GenreService genreService;
    MovieService movieService;
    ContractService contractService;

    public MessageHandler(ActorService serv){
        actorService = serv;
    }

    public MessageHandler(ActorService serv1, GenreService serv2){
        actorService = serv1;
        genreService = serv2;
    }

    public MessageHandler(ActorService s1, GenreService s2, MovieService s3){
        actorService = s1;
        genreService = s2;
        movieService = s3;
    }

    public MessageHandler(ActorService s1, GenreService s2, MovieService s3, ContractService s4){
        actorService = s1;
        genreService = s2;
        movieService = s3;
        contractService = s4;
    }

    public Message addActor(Message request){
        Actor actor = Factory.getActor(request.getBody());

        try{
            if(actorService.addActor(actor).get().isPresent())
                return new Message("ok", "ok");
            else
                return new Message("error", "duplicate actor");
        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message deleteActor(Message request){
        try{
            actorService.deleteActor(Long.parseLong(request.getBody()));
            return new Message("ok", "ok");
        }
        catch(RuntimeException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getActors(Message request){
        try{
            Set<Actor> actors = actorService.getAllActors().get();
            String answer = actors.stream().map(Factory::actorString).reduce("", (a, b) -> a + b + ";");
            //System.out.println("ACTORS IN SERVER: " + answer);


            return new Message("ok", answer);
        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message findActor(Message request){

        try{
            Long ID = Long.parseLong(request.getBody());
            //System.out.println("ID is "+ ID);
            Actor foundActor = actorService.findActor(Long.parseLong(request.getBody())).get();
            //System.out.println("ACTOR IS: " + foundActor.toString());

            String answer = Factory.actorString(foundActor);

            return new Message("ok", answer);

        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message updateActor(Message request){
        try{
            actorService.updateActor(Factory.getActor(request.getBody()));
            return new Message("ok", "ok");
        }
        catch(RuntimeException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message addGenre(Message request){
        Genre genre = Factory.getGenre(request.getBody());

        try{
            if(genreService.addGenre(genre).get().isPresent()){
                return new Message("ok", "ok");
            }
            else
                return new Message("error", "duplicate genre");
        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }


    public Message getGenres(Message request){
        try{
            Set<Genre> genres = genreService.getAllGenres().get();
            String answer = genres.stream().map(Factory::genreString).reduce("", (a,b)-> a+b+";");

            //System.out.println("GENRES SERVER: " + answer);

            return new Message("ok", answer);
        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }


    public Message findGenre(Message request){
        try{
            Long ID = Long.parseLong(request.getBody());
            var foundGenre = genreService.getById(ID).get();

            if(foundGenre.isPresent()){
                String answer = Factory.genreString(foundGenre.get());
                return new Message("ok", answer);
            }
            else
                return new Message("error", "error");

        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message deleteGenre(Message request){
        try {
            genreService.deleteGenre(Long.parseLong(request.getBody()));
            return new Message("ok", "ok");
        }
        catch (Exception e){
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message updateGenre(Message request){
        try{
            genreService.updateGenre(Factory.getGenre(request.getBody()));
            return new Message("ok", "ok");
        }
        catch(RuntimeException e){
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message addMovie(Message request){
        System.out.println("HELLO???");
        try{
            Movie movie = Factory.getMovie(request.getBody());
            System.out.println("MOVIE IS: " + movie);

            movieService.addMovie(Factory.getMovie(request.getBody()));
            return new Message("ok", "ok");
        } catch (ValidatorException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message printMovies(Message request){
        try {
            Set<Movie> movies = movieService.getAllMovies().get();
            String answer = movies.stream().map(Factory::movieString).reduce("", (a, b)-> a+b+";");

            return new Message("ok", answer);
        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message deleteMovie(Message request){
        try {
            movieService.deleteMovie(Long.parseLong(request.getBody()));
            return new Message("ok", "ok");
        }
        catch (Exception e){
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message updateMovie(Message request){
        try{
            movieService.updateMovie(Factory.getMovie(request.getBody()));
            return new Message("ok", "ok");
        }
        catch(RuntimeException e){
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message findMovie(Message request){
        try {
            System.out.println("MESSAGE: " + request.getBody());
            Long ID = Long.parseLong(request.getBody());
            var foundMovie = movieService.findMovie(ID).get();
            System.out.println("MESSAGE LONG: " + ID);

            if(foundMovie.isPresent()){
                String answer = Factory.movieString(foundMovie.get());
                return new Message("ok", answer);
            }
            else
                return new Message("error", "error");

        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message addContract(Message request){
        try{
            Contract contract = Factory.getContract(request.getBody());
            contractService.addContract(contract);

            return new Message("ok", "ok");
        }
        catch (Exception e){
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message printContracts(Message request){
        try{
            Set<Contract> contract = contractService.getAllContracts().get();
            String answer = contract.stream().map(Factory::contractString).reduce("", (a,b)-> a + b + ";");

            return new Message("ok", answer);
        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message findContract(Message request){
        try{
            Long ID = Long.parseLong(request.getBody());
            var foundContract = contractService.findContract(ID).get();

            if(foundContract.isPresent()){
                String answer = Factory.contractString(foundContract.get());
                return new Message("ok", answer);
            }
            else
                return new Message("error", "error");
        } catch (ExecutionException | InterruptedException e) {
            return new Message("error", e.getCause().getMessage());
        }
    }
}


