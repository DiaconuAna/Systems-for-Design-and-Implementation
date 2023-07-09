package ro.ubb.socket.client.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ubb.socket.client.service.ActorServiceImpl;
import ro.ubb.socket.client.service.ContractServiceImpl;
import ro.ubb.socket.client.service.GenreServiceImpl;
import ro.ubb.socket.client.service.MovieServiceImpl;
import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.client.ui.ClientConsole;
import ro.ubb.socket.domain.Contract;
import ro.ubb.socket.service.ActorService;
import ro.ubb.socket.service.ContractService;
import ro.ubb.socket.service.GenreService;
import ro.ubb.socket.service.MovieService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
public class AppConfig {
    @Bean
    ExecutorService executorService(){
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    TcpClient tcpClient(){
        return new TcpClient(executorService());
    }

    @Bean
    ActorService actorService(){
        return new ActorServiceImpl();
    }

    @Bean
    ContractService contractService(){
        return new ContractServiceImpl();
    }

    @Bean
    GenreService genreService(){
        return new GenreServiceImpl();
    }

    @Bean
    MovieService movieService(){
        return new MovieServiceImpl();
    }

    @Bean
    ClientConsole clientConsole(){
        return new ClientConsole();
    }
}
