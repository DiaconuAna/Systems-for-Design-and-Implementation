package catalog.client.ui;

import catalog.client.exception.ClientException;
import catalog.web.converter.ActorConverter;
import catalog.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class ClientConsole {
    @Autowired
    private RestTemplate restTemplate;

    String actorUrl = "http://localhost:8080/api/actors";
    String genreUrl = "http://localhost:8080/api/genres";
    String movieUrl = "http://localhost:8080/api/movies";
    String contractUrl = "http://localhost:8080/api/contracts";


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
        System.out.println("15. Filter movies by genre");
        System.out.println("16. Sort actors by popularity");
        System.out.println("0. Exit.");
    }


    public void printGender(){
        System.out.println("Choose a gender: ");
        System.out.println("0. Non-binary");
        System.out.println("1. Female");
        System.out.println("2. Male");

    }

    // list entities

    private void printActors() throws ClientException {
        // provisional
        ActorsDto actors = restTemplate.getForObject(actorUrl, ActorsDto.class);
        if(actors == null)
            throw new ClientException("Cannot get actors from server");
        else{
            System.out.println("***********");

            var res = actors.getActors().stream().
                    map(actor -> actor.getId() + "." + actor.getFirstName() + " " + actor.getLastName() + " Age: " + actor.getAge() +" Gender: " + actor.getGender()).collect(Collectors.toList());
            for(var r: res)
                System.out.println(r);

            System.out.println("***********");

            //System.out.println(actors.getActors().stream().map(actorDto -> actorConverter.convertDtoToModel(actorDto)));
            // return actors.getActors().stream().map(actorDto -> new Actor())
        }

    }

    private void listContracts() throws ClientException{
        ContractsDto contracts = restTemplate.getForObject(contractUrl, ContractsDto.class);

        if(contracts == null)
            throw new ClientException("Cannot get contracts from server");
        else{
            System.out.println("***********");
            //System.out.println(contracts.getContracts());
            for(var contract: contracts.getContracts()){
                //                 var genre = restTemplate.getForObject(genreUrl + "/{id}", GenreDto.class, movie.getGenreId());
                var actor = restTemplate.getForObject(actorUrl + "/{id}", ActorDto.class, contract.getActorId());
                var movie = restTemplate.getForObject(movieUrl + "/{id}", MovieDto.class, contract.getMovieId());
                System.out.println("Contract: " + contract.getId());
                System.out.println("   Actor: " + actor.getId() + "." + actor.getFirstName() + " " + actor.getLastName());
                System.out.println("   Movie: " + movie.getId() + "." + movie.getTitle() + " Genre: " + movie.getGenreId() + " Director: " + movie.getDirector());
                // to do : make it prettier
            }
            System.out.println("***********");
        }
    }

    private void listGenres() throws ClientException {
        GenresDto genres = restTemplate.getForObject(genreUrl, GenresDto.class);

        if(genres == null)
            throw new ClientException("Cannot get genres from server");
        else{
            System.out.println("***********");
            var res = genres.getGenres().stream().map(genre -> genre.getId() + "." + "Name: " + genre.getName() + " Description: " + genre.getDescription()).collect(Collectors.toList());

            for(var r: res)
                System.out.println(r);
            System.out.println("***********");
        }
    }

    private void listMovies() throws ClientException{
        MoviesDto movies = restTemplate.getForObject(movieUrl, MoviesDto.class);

        if(movies == null)
            throw new ClientException("Cannot get movies from server");
        else{
            GenresDto genresDto =  restTemplate.getForObject(genreUrl, GenresDto.class);

            // to do: that s how you get a movie genre
            //var genre = restTemplate.getForObject(genreUrl + "/{id}", GenreDto.class, 1);
            //System.out.println("GENRES: " + genre);
            System.out.println("***********");
            for(var movie: movies.getMovies()){
                var genre = restTemplate.getForObject(genreUrl + "/{id}", GenreDto.class, movie.getGenreId());
                var toPrint = movie.getId() + "." + movie.getTitle() + ";\n  Director: " + movie.getDirector() + ";\n  Production Country: " + movie.getProductionCountry();
                assert genre != null;
                System.out.println( toPrint + ";\n  Genre: " + genre.getName());
            }
            System.out.println("***********");
            //System.out.println(movies.getMovies().stream().map(movie -> movie + "\nMovie genre: " + movie.getGenre() + "\n").collect(Collectors.toList()));
        }
    }

    // add entities

    public void addActor(){
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

        ActorDto actorToSave = restTemplate.postForObject(actorUrl,new ActorDto(first_name, last_name, age, g, popularity), ActorDto.class);
        System.out.println("Saved actor: " + actorToSave);

    }

    private void addGenre(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Genre Name: ");
        String name = scanner.nextLine();

        System.out.println("Genre Description: ");
        String descr = scanner.nextLine();

        System.out.println("NAME: " + name + " GENRE: " + descr);

        GenreDto genreToSave = restTemplate.postForObject(genreUrl, new GenreDto(name, descr), GenreDto.class);
        System.out.println("Saved genre: " + genreToSave);

    }

    private void addMovie() throws ExecutionException, InterruptedException, ClientException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Movie Title: ");
        String title = scanner.nextLine();

        System.out.println("Director name: ");
        String dirName = scanner.nextLine();

        System.out.println("Production Country: ");
        String productionCountry = scanner.nextLine();

        long genreId = 0;
        boolean foundGenre = false;

        GenresDto genresDto =  restTemplate.getForObject(genreUrl, GenresDto.class);
        var ids = genresDto.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList());

        while(!foundGenre) {
            System.out.println("Choose a genre ID from the list: ");
            listGenres();
            genreId = scanner.nextLong();
            if(ids.contains(genreId))
                foundGenre = true;
        }

        MovieDto movieToSave = restTemplate.postForObject(movieUrl, new MovieDto(title, dirName, genreId, productionCountry), MovieDto.class);
        System.out.println("Saved movie: " + movieToSave);

    }

    private void addContract() throws ClientException {

        Scanner scanner = new Scanner(System.in);

        boolean gotMovie = false;
        Long movieId = new Long(1);

        MoviesDto moviesDto = restTemplate.getForObject(movieUrl, MoviesDto.class);
        var ids = moviesDto.getMovies().stream().map(m -> m.getId()).collect(Collectors.toList());

        while(!gotMovie){
            listMovies();

            System.out.println("Choose a movie ID from the list: ");
            movieId = scanner.nextLong();

            if(ids.contains(movieId))
                gotMovie = true;
        }

        boolean gotActor = false;
        Long actorId = new Long(1);

        ActorsDto actorsDto = restTemplate.getForObject(actorUrl, ActorsDto.class);
        var actorids = actorsDto.getActors().stream().map(a -> a.getId()).collect(Collectors.toList());

        while(!gotActor){
            printActors();

            System.out.println("Choose an actor ID from the list: ");
            actorId = scanner.nextLong();

            if(actorids.contains(actorId))
                gotActor = true;

        }

        ContractDto contractToSave = restTemplate.postForObject(contractUrl, new ContractDto(actorId, movieId), ContractDto.class);
        System.out.println("Saved contract: " + contractToSave);
    }

    // delete entities

    private void deleteActor(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ID: ");

        long id = scanner.nextLong();

        restTemplate.delete(actorUrl + "/{id}", id);
        System.out.println("delete: ");
        System.out.println(restTemplate.getForObject(actorUrl, ActorsDto.class));
    }

    private void deleteMovie(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID of the movie you want to delete: ");
        long id = scanner.nextLong();

        restTemplate.delete(movieUrl + "/{id}", id);
    }

    private void deleteGenre(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID of the genre you want to delete: ");
        long id = scanner.nextLong();
        restTemplate.delete(genreUrl + "/{id}", id);
    }


    // update entities

    private void updateActor() throws ExecutionException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ID: ");
        Long id = scanner.nextLong();

        scanner.nextLine();

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

        ActorDto actorDto = new ActorDto(first_name, last_name, age, g, popularity);

        try {
            System.out.println(actorDto);
            actorDto.setId(id); //?? it works :')
            restTemplate.put(actorUrl + "/{id}", actorDto, id);
            System.out.println("Actor updated successfully!");
        } catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update this actor!" + ex.getMessage());
        }

    }

    private void updateGenre(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ID of the genre you want to update: ");
        long id = scanner.nextLong();

        scanner.nextLine();

        System.out.println("Genre Name: ");
        String name = scanner.nextLine();

        System.out.println("Genre Description: ");
        String descr = scanner.nextLine();

        GenreDto genreDto = new GenreDto(name, descr);

        try{
            genreDto.setId(id);
            System.out.println(genreDto);
            restTemplate.put(genreUrl + "/{id}", genreDto, id);
            System.out.println(genreDto);
            System.out.println("Genre updated successfully!");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update this genre!" + ex.getMessage());
        }

    }

    private void updateMovie() throws ClientException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ID: ");
        Long id = scanner.nextLong();

        scanner.nextLine();

        System.out.println("Movie Title: ");
        String title = scanner.nextLine();

        System.out.println("Director name: ");
        String dirName = scanner.nextLine();

        System.out.println("Production Country: ");
        String productionCountry = scanner.nextLine();

        long genreId = 0;
        boolean foundGenre = false;

        GenresDto genresDto =  restTemplate.getForObject(genreUrl, GenresDto.class);
        var ids = genresDto.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList());

        while(!foundGenre) {
            System.out.println("Choose a genre ID from the list: ");
            listGenres();
            genreId = scanner.nextLong();
            if(ids.contains(genreId))
                foundGenre = true;
        }

        MovieDto movieDto = new MovieDto(title, dirName, genreId, productionCountry);

        try{
            movieDto.setId(id);
            System.out.println(movieDto);
            restTemplate.put(movieUrl + "/{id}", movieDto, id);
            System.out.println(movieDto);
            System.out.println("Movie updated successfully!");
        }catch (HttpStatusCodeException ex) {
            System.out.println("Cannot update this movie!" + ex.getMessage());
        }
    }

    // filtering

    private void filterByGenre() throws ClientException {
        Scanner scanner = new Scanner(System.in);

        long genreId = 0;
        boolean foundGenre = false;

        GenresDto genresDto =  restTemplate.getForObject(genreUrl, GenresDto.class);
        var ids = genresDto.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList());

        while(!foundGenre) {
            System.out.println("Choose a genre ID from the list: ");
            listGenres();
            genreId = scanner.nextLong();
            if(ids.contains(genreId))
                foundGenre = true;
        }

        var filteredMovies = restTemplate.getForObject(movieUrl + "/filter/{genreId}", MoviesDto.class, genreId);

        if(filteredMovies.getMovies().isEmpty())
            System.out.println("There are no movies for this genre");
        else {
            var res = filteredMovies.getMovies();

            for(var m: res){
                System.out.println(m.getId() + "." + m.getTitle() + " ;Director: " + m.getDirector() + " ;Production Country: " + m.getProductionCountry());
                var genre = restTemplate.getForObject(genreUrl + "/{id}", GenreDto.class, m.getGenreId());
                System.out.println("   Genre: " + genre.getName() + " - " + genre.getDescription());
            }
        }
    }

    // sort actors by popularity

    private void sortActors(){
        ActorsDto actorsDto = restTemplate.getForObject(actorUrl + "/sort/popularity", ActorsDto.class);
        System.out.println("Sorted actors: " + actorsDto);

        if(actorsDto == null)
            System.out.println("No actors to be sorted");
        else {
            System.out.println("***********");

            var res = actorsDto.getActors().stream().
                    map(actor -> actor.getId() + "." + actor.getFirstName() + " " + actor.getLastName() + " Age: " + actor.getAge() +" Gender: " + actor.getGender() +" Popularity: " + actor.getPopularity())
                    .collect(Collectors.toCollection(LinkedHashSet::new));;
            for(var r: res)
                System.out.println(r);

            System.out.println("***********");

            //actorsDto.getActors().forEach(System.out::println);
        }

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
                    printActors();
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
                else if(option == 5)
                    listGenres();
                else if(option == 6)
                    addGenre();
                else if(option == 7)
                    deleteGenre();
                else if(option == 8)
                    updateGenre();
                else if(option == 9)
                    listMovies();
                else if(option == 10)
                    addMovie();
                else if(option == 11)
                    deleteMovie();
                else if(option == 12)
                    updateMovie();
                else if(option == 13)
                    addContract();
                else if(option == 14)
                    listContracts();
                else if(option == 15)
                    filterByGenre();
                else if(option == 16)
                    sortActors();

                // to do - properly print actors and movies - genre for movies
                // update movies
                // the filter thing
                // crud for contract entity

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

//        StudentDto savedStudent = restTemplate.postForObject(url,
//                new StudentDto("saved-st", 10),
//                StudentDto.class);
//        System.out.println("saved student:");
//        System.out.println(savedStudent);
//
//        savedStudent.setName("update-st");
//        restTemplate.put(url + "/{id}", savedStudent, savedStudent.getId());
//        System.out.println("update:");
//        System.out.println(restTemplate.getForObject(url, StudentsDto.class));
//
//        restTemplate.delete(url + "/{id}", savedStudent.getId());
//        System.out.println("delete:");
//        System.out.println(restTemplate.getForObject(url, StudentsDto.class));
    }
}
