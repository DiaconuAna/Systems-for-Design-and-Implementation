package ro.ubb.socket.server.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ubb.socket.domain.validators.ActorValidator;
import ro.ubb.socket.domain.validators.ContractValidator;
import ro.ubb.socket.domain.validators.GenreValidator;
import ro.ubb.socket.domain.validators.MovieValidator;
import ro.ubb.socket.server.repository.ActorRepositoryImpl;
import ro.ubb.socket.server.repository.ContractRepositoryImpl;
import ro.ubb.socket.server.repository.GenreRepositoryImpl;
import ro.ubb.socket.server.repository.MovieRepositoryImpl;
import ro.ubb.socket.server.service.ActorServiceImpl;
import ro.ubb.socket.server.service.ContractServiceImpl;
import ro.ubb.socket.server.service.GenreServiceImpl;
import ro.ubb.socket.server.service.MovieServiceImpl;
import ro.ubb.socket.server.tcp.MessageHandler;
import ro.ubb.socket.server.tcp.TcpServer;
import ro.ubb.socket.service.ActorService;
import ro.ubb.socket.service.ContractService;
import ro.ubb.socket.service.GenreService;
import ro.ubb.socket.service.MovieService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    @Bean
    ActorValidator actorValidator(){
        return new ActorValidator();
    }

    @Bean
    ActorRepositoryImpl actorRepository(){
        return new ActorRepositoryImpl(actorValidator());
    }

    @Bean
    ActorService actorService(){
        return new ActorServiceImpl(actorRepository());
    }

    @Bean
    MovieValidator movieValidator(){
        return new MovieValidator();
    }

    @Bean
    MovieRepositoryImpl movieRepository(){return new MovieRepositoryImpl(movieValidator());}

    @Bean
    MovieService movieService(){ return new MovieServiceImpl(movieRepository());}

    @Bean
    ContractValidator contractValidator(){
        return new ContractValidator();
    }

    @Bean
    ContractRepositoryImpl contractRepository(){return new ContractRepositoryImpl(contractValidator());}

    @Bean
    ContractService contractService(){ return new ContractServiceImpl(contractRepository());}

    @Bean
    GenreValidator genreValidator(){
        return new GenreValidator();
    }

    @Bean
    GenreRepositoryImpl genreRepository(){ return new GenreRepositoryImpl(genreValidator());}

    @Bean
    GenreService genreService(){ return new GenreServiceImpl(genreRepository());
    }

    @Bean
    ExecutorService executorService(){
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    TcpServer tcpServer(){
        return new TcpServer(1234);
    }

    @Bean
    MessageHandler messageHandler(){
        return new MessageHandler(actorService(), genreService(), movieService(),  contractService());
    }
    }

    // to do message handler as a bean

