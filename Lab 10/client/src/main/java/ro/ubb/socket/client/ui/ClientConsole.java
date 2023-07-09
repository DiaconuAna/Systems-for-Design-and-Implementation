package ro.ubb.socket.client.ui;

import org.springframework.beans.factory.annotation.Autowired;
import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.Contract;
import ro.ubb.socket.domain.Genre;
import ro.ubb.socket.domain.Movie;
import ro.ubb.socket.service.ActorService;
import ro.ubb.socket.service.ContractService;
import ro.ubb.socket.service.GenreService;
import ro.ubb.socket.service.MovieService;

import java.lang.module.Configuration;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClientConsole {

    @Autowired
    private ActorService actorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ContractService contractService;

//    public ClientConsole(ActorService serv) {
//        this.actorService = serv;
//    }
//
//    public ClientConsole(ActorService s1, GenreService s2, MovieService s3, ContractService s4){
//        this.actorService = s1;
//        this.genreService = s2;
//        this.movieService = s3;
//        this.contractService = s4;
//    }
//


    public void printGender(){
        System.out.println("Choose a gender: ");
        System.out.println("0. Non-binary");
        System.out.println("1. Female");
        System.out.println("2. Male");

    }

    public void printUpdate(){
        System.out.println("1. Update first name");
        System.out.println("2. Update last name");
        System.out.println("3. Update age");
        System.out.println("4. Update popularity");
        System.out.println("5. Update gender");
    }

    public void printMenu(){
        System.out.println("1. List actors.");
        System.out.println("2. Add actor.");
        System.out.println("3. Delete actor.");
        System.out.println("4. Update actor.");
        System.out.println("5. List genres");
        System.out.println("6. Add genre");
        System.out.println("7. Delete genre");
        System.out.println("8. Update genre");
        System.out.println("9. List movies");
        System.out.println("10. Add movie");
        System.out.println("11. Delete movie");
        System.out.println("12. Update movie");
        System.out.println("13. Cast an actor to a movie");
        System.out.println("14. View movie-actor contracts");
        System.out.println("0. Exit.");
    }

    public void printUpdateGenre(){
        System.out.println("1. Update name");
        System.out.println("2. Update description");
    }

    public void printUpdateMovie(){
        System.out.println("1. Update title");
        System.out.println("2. Update movie director");
        System.out.println("3. Update movie production country");
        System.out.println("4. Update movie genre");

    }

    public void runConsole() {
        Scanner scanner = new Scanner(System.in);
        boolean over = false;
        while (!over) {
            try {
                printMenu();
                System.out.println("Enter option>>> ");

                int option = scanner.nextInt();
                if(option == 0)
                    over = true;
                else if (option == 1)
                    listActors2();
                else
                if (option == 2) {
                    addActor();
                }
                else if(option == 3) {
                    deleteActor();
                }
                else if(option == 4){
                    updateActor();
                }
                else if(option == 6){
                    addGenre();
                }
                else if(option == 5) {
                    listGenres();
                }
                else if(option == 7){
                    deleteGenre();
                }
                else if(option == 8){
                    updateGenre();
                }
                else if(option == 9){
                    listMovies();
                }
                else if(option == 10){
                    addMovie();
                }
                else if(option == 11){
                    deleteMovie();
                }
                else if(option == 12){
                    updateMovie();
                }
                else if(option == 13){
                    createContract();
                }
                else if(option == 14){
                    displayContracts();
                }
                else
                 {
                    System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // pick a movie

    // pick an actor

    // figure out how to cast them
    // steps
    // you get the id from the actor, the id from the movie
    // add them to actormovies database (how???)

    // for the actor, add the movie id to the array list
    // for the movie, add the actor id to the array list

    // create a new entity - CONTRACT - maintains the many to many between the actor and the movie

    private void displayContracts() {
//        contractService.getAllContracts().thenAcceptAsync(
//          contracts -> contracts.forEach(System.out::println)
//        );
        try {
            var contracts = contractService.getAllContracts().get();
            for(Contract c: contracts) {
                System.out.println("Contract: " + c.getId());
                System.out.println("  Actor: " + actorService.findActor(c.getActorId()).get());
                System.out.println("  Movie: " + movieService.findMovie(c.getMovieId()).get());
                System.out.println(" ----- ");
            }
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }



    }

    private void createContract(){
        Scanner scanner = new Scanner(System.in);



        try {
            var movies = movieService.getAllMovies().get().stream().map(Movie::getId).collect(Collectors.toSet());
            boolean gotMovie = false;
            Long movieId = Long.parseLong("0");

            while (!gotMovie) {
                System.out.println("Enter movie ID: ");
                movieId = scanner.nextLong();

                if (movies.contains(movieId))
                    gotMovie = true;
            }

            //System.out.println("Enter actor ID: ");
            var actors = actorService.getAllActors().get().stream().map(Actor::getId).collect(Collectors.toSet());
            boolean gotActor = false;
            Long actorId = Long.parseLong("0");

            while(!gotActor){
                System.out.println("Enter actor ID: ");
                actorId = scanner.nextLong();

                if(actors.contains(actorId))
                    gotActor = true;
            }

            System.out.println("Enter contract ID: ");
            Long contractId = scanner.nextLong();

            Contract contract = new Contract(contractId, actorId, movieId);

            contractService.addContract(contract);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void updateMovieTitle(Movie movie){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a new title: ");
        String title = scanner.nextLine();

        Movie updatedMovie = new Movie(title, movie.getDirector(), movie.getProductionCountry(), movie.getGenre(), movie.getId());
        movieService.updateMovie(updatedMovie);

    }

    private void updateMovieDirector(Movie movie){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a new director name: ");
        String dirName = scanner.nextLine();

        Movie updatedMovie = new Movie(movie.getTitle(), dirName, movie.getProductionCountry(), movie.getGenre(), movie.getId());
        movieService.updateMovie(updatedMovie);
    }

    private void updateMovieCountry(Movie movie){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a new production country: ");
        String country = scanner.nextLine();

        Movie updatedMovie = new Movie(movie.getTitle(), movie.getDirector(), country, movie.getGenre(), movie.getId());
        movieService.updateMovie(updatedMovie);
    }

    private void updateMovieGenre(Movie movie) throws ExecutionException, InterruptedException {

        Scanner scanner = new Scanner(System.in);
        boolean gotGenre = false;
        long Id = 0;

        var genreIds = genreService.getAllGenres().get().stream().map(Genre::getId).collect(Collectors.toSet());

        while(!gotGenre){
            System.out.println("Choose a new genre ID from the list: ");
            listGenres();

            Id = scanner.nextLong();

            if(genreIds.contains(Id))
                gotGenre = true;
        }

        Movie updatedMovie = new Movie(movie.getTitle(), movie.getDirector(), movie.getProductionCountry(), Id, movie.getId());
        movieService.updateMovie(updatedMovie);
    }

    private void updateMovie() throws ExecutionException, InterruptedException {

        Scanner scanner = new Scanner(System.in);

        boolean gotMovie = false;
        long MovieId = 0;

        var movieIds = movieService.getAllMovies().get().stream().map(Movie::getId).collect(Collectors.toSet());

        while(!gotMovie){

            listMovies();


            System.out.println("Enter Movie ID: ");
            MovieId = scanner.nextLong();

            if(movieIds.contains(MovieId))
                gotMovie = true;

        }

        try {
            System.out.println("HEY " + MovieId);
            Optional<Movie> m = movieService.findMovie(MovieId).get();

            if(m.isPresent()){
                Movie movie = m.get();
                printUpdateMovie();

                System.out.println("Pick a choice: ");
                int choice = scanner.nextInt();

                if(choice == 1)
                    updateMovieTitle(movie);
                else if(choice == 2)
                    updateMovieDirector(movie);
                else if(choice == 3)
                    updateMovieCountry(movie);
                else if(choice == 4)
                    updateMovieGenre(movie);
                else
                    System.out.println("Invalid choice");
                // else throw sth here
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void deleteMovie(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID of the movie you want to delete: ");
        long id = scanner.nextLong();
        movieService.deleteMovie(id);
    }

    private void listActors2(){
//        actorService.getAllActors().thenAcceptAsync(
//                actors -> actors.forEach(System.out::println)
//        );

        System.out.println("****ACTORS****");
        try{
            System.out.println("----------------------");

            var actors = actorService.getAllActors().get();

            for(Actor a: actors){

                System.out.println(a);
                var contracts = contractService.getAllContracts().get();
                var movies = movieService.getAllMovies().get();

                var actorContracts = contracts.stream().filter(c -> c.getActorId() == a.getId()).map(Contract::getMovieId).collect(Collectors.toSet());
                var actorMovies = movies.stream().filter(c-> actorContracts.contains(c.getId())).collect(Collectors.toSet());

                if(!actorMovies.isEmpty()){
                    System.out.println("Movies starring the actor: ");

                    for(Movie m: actorMovies)
                        System.out.println("    " + m);
                }

            }

            System.out.println("----------------------");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    // todo - when printing movies, get the genre based on the genre id and print it properly
    // also print the actors when you work it out
    private void listMovies(){

        System.out.println("*****MOVIES*****");

        try{
            System.out.println("----------------------");
            var movies = movieService.getAllMovies().get();

            for(Movie m: movies){
                //System.out.println("MOVIE GENRE IS: " + m.getGenre());
                var genreId = m.getGenre();
                var optionalGenre = genreService.getById(genreId).get();
                if(optionalGenre.isPresent()){
                    Genre movieGenre = optionalGenre.get();
                    System.out.println(m + "\n    " + movieGenre);
                }

                var actors = actorService.getAllActors().get();
                var contracts = contractService.getAllContracts().get();

                // get all actors cast in a movie
                var movieContracts = contracts.stream().filter(c -> c.getMovieId() == m.getId()).map(Contract::getActorId).collect(Collectors.toSet());
                var movieActors = actors.stream().filter(c -> movieContracts.contains(c.getId())).collect(Collectors.toSet());

                if(!movieActors.isEmpty()){
                    System.out.println("Actors in movie: ");

                    for(Actor a: movieActors)
                        System.out.println("    " + a);
                }

                System.out.println("----------------------");
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void addMovie() throws ExecutionException, InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Movie Title: ");
        String title = scanner.nextLine();

        System.out.println("Director name: ");
        String dirName = scanner.nextLine();

        System.out.println("Production Country: ");
        String productionCountry = scanner.nextLine();

        boolean gotGenre = false;
        long Id = 0;

        var genreIds = genreService.getAllGenres().get().stream().map(Genre::getId).collect(Collectors.toSet());

        while(!gotGenre){
            System.out.println("Choose a genre ID from the list: ");
            listGenres();

            Id = scanner.nextLong();

            if(genreIds.contains(Id))
                gotGenre = true;
        }

        boolean gotMovie = false;
        long MovieId = 0;

        var movieIds = movieService.getAllMovies().get().stream().map(Movie::getId).collect(Collectors.toSet());

        while(!gotMovie){
            System.out.println("Enter a valid movie ID: ");

            MovieId = scanner.nextLong();

            if(!movieIds.contains(MovieId))
                gotMovie = true;

        }

        //System.out.println("Movie ID:");

        //var movieGenreAux = genreService.getById(Id).get();
        //Genre movieGenre = new Genre();

        //if(movieGenreAux.isPresent())
        //    movieGenre = movieGenreAux.get();

        Movie movie = new Movie(title, dirName, productionCountry, Id, MovieId);

        movieService.addMovie(movie);
    }

    private void addGenre(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Genre Name: ");
        String name = scanner.nextLine();

        System.out.println("Genre Description: ");
        String descr = scanner.nextLine();

        System.out.println("Genre ID: ");
        long id = scanner.nextLong();

        Genre genre = new Genre(id, name, descr);

        genreService.addGenre(genre);

    }

    private void deleteGenre(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID of the genre you want to delete: ");
        long id = scanner.nextLong();
        genreService.deleteGenre(id);

    }

    private void listGenres(){
        genreService.getAllGenres().thenAcceptAsync(
          genres -> genres.forEach(System.out::println)
        );
    }

    private void listActors(){
        actorService.getAllActors().thenAcceptAsync(
                actors -> actors.forEach(System.out::println)
        );
    }

    private void deleteActor(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ID: ");

        long id = scanner.nextLong();

        actorService.deleteActor(id);
    }

    private void addActor(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input first name: ");
        String first_name = scanner.nextLine();

        System.out.println("Input last name: ");
        String last_name = scanner.nextLine();

        System.out.println("Input age: ");
        int age = scanner.nextInt();

        System.out.println("Input actor's popularity (from 0 to 10): ");
        int popularity = scanner.nextInt();

        while(popularity < 0 || popularity > 10){
            System.out.println("Input actor's popularity (from 0 to 10): ");
            popularity = scanner.nextInt();
        }

        printGender();
        int gender = scanner.nextInt();

        while(gender < 0 || gender > 2){
            printGender();
            gender = scanner.nextInt();
        }

        String g = "";
        if(gender == 0)
            g = "non-binary";
        if(gender == 1)
            g = "female";
        if(gender == 2)
            g = "male";


        Actor actor = new Actor(first_name, last_name, age, popularity, g);

        System.out.println("Enter ID: ");
        long id = scanner.nextLong();
        actor.setId(id);

        actorService.addActor(actor);
    }

    private void updateFirstName(Actor a){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input new first name: ");
        String first = scanner.nextLine();

        Actor updatedActor = new Actor(first, a.getLastName(), a.getAge(), a.getPopularity(), a.getGender());
        updatedActor.setId(a.getId());

        actorService.updateActor(updatedActor);
    }

    private void updateLastName(Actor a){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input new last name: ");
        String last = scanner.nextLine();

        Actor updatedActor = new Actor(a.getFirstName(), last, a.getAge(), a.getPopularity(), a.getGender());
        updatedActor.setId(a.getId());
        actorService.updateActor(updatedActor);
    }

    private void updateAge(Actor a){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input new age: ");
        int age = scanner.nextInt();

        Actor updatedActor = new Actor(a.getFirstName(), a.getLastName(), age, a.getPopularity(), a.getGender());
        updatedActor.setId(a.getId());
        actorService.updateActor(updatedActor);
    }

    private void updatePopularity(Actor a){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input new popularity(from 0 to 10): ");
        int popularity = scanner.nextInt();

        while(popularity < 0 || popularity > 10){
            System.out.println("Input actor's popularity (from 0 to 10): ");
            popularity = scanner.nextInt();
        }

        Actor updatedActor = new Actor(a.getFirstName(), a.getLastName(), a.getAge(), popularity, a.getGender());
        updatedActor.setId(a.getId());
        actorService.updateActor(updatedActor);
    }

    private void updateGender(Actor a){
        Scanner scanner = new Scanner(System.in);

        printGender();
        int gender = scanner.nextInt();

        while(gender < 0 || gender > 2){
            printGender();
            gender = scanner.nextInt();
        }

        String g = "";
        if(gender == 0)
            g = "non-binary";
        if(gender == 1)
            g = "female";
        if(gender == 2)
            g = "male";

        Actor updatedActor = new Actor(a.getFirstName(), a.getLastName(), a.getAge(), a.getPopularity(), g);
        updatedActor.setId(a.getId());
        actorService.updateActor(updatedActor);
    }


    private void updateActor() throws ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID: ");

        long id = scanner.nextLong();

        CompletableFuture<Actor> a = actorService.findActor(id);

        Actor actor = a.get();
        System.out.println("ACTOR IS: " + actor);
        printUpdate();

        int choice = scanner.nextInt();

            if(choice == 1)
                updateFirstName(actor);
            else if(choice == 2)
                updateLastName(actor);
            else if(choice == 3)
                updateAge(actor);
            else if(choice == 4)
                updatePopularity(actor);
            else if(choice == 5)
                updateGender(actor);
            else
                System.out.println("Invalid choice");

    }

    public void updateGenreName(Genre genre){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter new genre name: ");
        String name = scanner.nextLine();

        Genre updatedGenre = new Genre(genre.getId(), name, genre.getDescription());
        genreService.updateGenre(updatedGenre);

    }

    public void updateGenreDescription(Genre genre){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter new genre description: ");
        String descr = scanner.nextLine();

        Genre updatedGenre = new Genre(genre.getId(), genre.getName(), descr);
        genreService.updateGenre(updatedGenre);
    }

    public void updateGenre() throws ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID: ");

        long id = scanner.nextLong();

        var genre = genreService.getById(id).get();
        if(genre.isPresent()){
            printUpdateGenre();
            int choice = scanner.nextInt();

            if(choice == 1)
                updateGenreName(genre.get());
            else if(choice == 2)
                updateGenreDescription(genre.get());
            else
                System.out.println("Invalid choice");

        }
    }




}