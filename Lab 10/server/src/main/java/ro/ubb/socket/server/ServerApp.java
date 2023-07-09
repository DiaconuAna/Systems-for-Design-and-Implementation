package ro.ubb.socket.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.common.Message;
import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.Contract;
import ro.ubb.socket.domain.Genre;
import ro.ubb.socket.domain.Movie;
import ro.ubb.socket.domain.database.*;
import ro.ubb.socket.domain.validators.ActorValidator;
import ro.ubb.socket.domain.validators.ContractValidator;
import ro.ubb.socket.domain.validators.GenreValidator;
import ro.ubb.socket.domain.validators.MovieValidator;
import ro.ubb.socket.repository.DatabaseRepository;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.server.repository.ActorRepositoryImpl;
import ro.ubb.socket.server.repository.ContractRepositoryImpl;
import ro.ubb.socket.server.service.ActorServiceImpl;
import ro.ubb.socket.server.service.ContractServiceImpl;
import ro.ubb.socket.server.service.GenreServiceImpl;
import ro.ubb.socket.server.service.MovieServiceImpl;
import ro.ubb.socket.server.tcp.*;
import ro.ubb.socket.service.ActorService;
import ro.ubb.socket.service.ContractService;
import ro.ubb.socket.service.GenreService;
import ro.ubb.socket.service.MovieService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(
//                Runtime.getRuntime().availableProcessors()
//        );

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ro/ubb/socket/server/config");

//        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
//        for(String beanName : allBeanNames) {
//            System.out.println(beanName);
//        }
        System.out.println("BEANS : ");

        String[] beans = context.getBeanDefinitionNames();

        for(String bn: beans)
            System.out.println(bn);

        System.out.println("END BEANS");
        JdbcOperations jdbcOperations = context.getBean(JdbcOperations.class);
        //Repository<Long, Actor> actors = new ActorRepositoryImpl(new ActorValidator(), jdbcOperations);
        System.out.println("BEAN IN SERVER: " + jdbcOperations);
        //Repository<Long, Actor> actors = new DatabaseRepository<>(new ActorValidator(), new DatabaseConnectionDetails(), new ActorDatabaseAdapter());
        //ActorService actorService = new ActorServiceImpl(actors);
        ActorService actorService = context.getBean(ActorService.class);

        //Repository<Long, Genre> genres = new DatabaseRepository<>(new GenreValidator(), new DatabaseConnectionDetails(), new GenreDatabaseAdapter());
        //GenreService genreService = new GenreServiceImpl(genres);

        GenreService genreService = context.getBean(GenreService.class);

        //Repository<Long, Movie> movies = new DatabaseRepository<>(new MovieValidator(), new DatabaseConnectionDetails(), new MovieDatabaseAdapter());
        //MovieService movieService = new MovieServiceImpl(movies);

        MovieService movieService = context.getBean(MovieService.class);

        //Repository<Long, Contract> contracts = new DatabaseRepository<>(new ContractValidator(), new DatabaseConnectionDetails(), new ContractDatabaseAdapter());
        //ContractService contractService = new ContractServiceImpl(contracts);

        ContractService contractService = context.getBean(ContractService.class);

        //MessageHandler messageHandler = new MessageHandler(actorService, genreService, movieService, contractService);
        MessageHandler messageHandler = context.getBean(MessageHandler.class);
        TcpServer tcpServer = context.getBean(TcpServer.class);
        ExecutorService executorService = context.getBean(ExecutorService.class);
        //TcpServer tcpServer = new TcpServer(executorService, 1234);

        tcpServer.addHandler("add actor", messageHandler::addActor);
        tcpServer.addHandler("delete actor", messageHandler::deleteActor);
        tcpServer.addHandler("print actors", messageHandler::getActors);
        tcpServer.addHandler("find actor", messageHandler::findActor);
        tcpServer.addHandler("update actor", messageHandler::updateActor);

        tcpServer.addHandler("add genre", messageHandler::addGenre);
        tcpServer.addHandler("print genres", messageHandler::getGenres);
        tcpServer.addHandler("find genre", messageHandler::findGenre);
        tcpServer.addHandler("delete genre", messageHandler::deleteGenre);
        tcpServer.addHandler("update genre", messageHandler::updateGenre);

        tcpServer.addHandler("add movie", messageHandler::addMovie);
        tcpServer.addHandler("print movies", messageHandler::printMovies);
        tcpServer.addHandler("find movie", messageHandler::findMovie);
        tcpServer.addHandler("delete movie", messageHandler::deleteMovie);
        tcpServer.addHandler("update movie", messageHandler::updateMovie);

        tcpServer.addHandler("add contract", messageHandler::addContract);
        tcpServer.addHandler("print contracts", messageHandler::printContracts);
        tcpServer.addHandler("find contract", messageHandler::findContract);

        System.out.println("Server started");
        tcpServer.startServer();
        executorService.shutdown();
        System.out.println("bye server!!!");
    }
}