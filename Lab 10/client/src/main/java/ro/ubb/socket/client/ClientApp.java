package ro.ubb.socket.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.socket.client.service.*;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.client.ui.ClientConsole;
import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.domain.Actor;
import ro.ubb.socket.domain.database.ActorDatabaseAdapter;
import ro.ubb.socket.domain.database.DatabaseConnectionDetails;
import ro.ubb.socket.domain.validators.ActorValidator;
import ro.ubb.socket.repository.DatabaseRepository;
import ro.ubb.socket.repository.Repository;
import ro.ubb.socket.service.ActorService;
import ro.ubb.socket.service.ContractService;
import ro.ubb.socket.service.GenreService;
import ro.ubb.socket.service.MovieService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        System.out.println("ClientApp started the process");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ro.ubb.socket.client.config");


//        ExecutorService executorService = Executors.newFixedThreadPool(
//                Runtime.getRuntime().availableProcessors()
//        );

        //TcpClient tcpClient = new TcpClient(executorService);
        //Repository<Long, Actor> actors = new DatabaseRepository<>(new ActorValidator(), new DatabaseConnectionDetails(), new ActorDatabaseAdapter());
        //ActorService actorService = new ActorServiceImpl(executorService, tcpClient);
        //GenreService genreService = new GenreServiceImpl(executorService, tcpClient);
        //MovieService movieService = new MovieServiceImpl(executorService, tcpClient);
        //ContractService contractService = new ContractServiceImpl(executorService, tcpClient);

        //ClientConsole clientConsole = new ClientConsole(actorService, genreService, movieService, contractService);
        ExecutorService executorService = context.getBean(ExecutorService.class);
        ClientConsole clientConsole = context.getBean(ClientConsole.class);
        clientConsole.runConsole();

        System.out.println("bye client");

        executorService.shutdown();
    }
}