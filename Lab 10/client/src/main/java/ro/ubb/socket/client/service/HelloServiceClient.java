package ro.ubb.socket.client.service;

import ro.ubb.socket.client.tcp.TcpClient;
import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.common.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class HelloServiceClient implements HelloService {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public HelloServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> sayHello(String name) {
        //build a request of type Message
        //send the request and receive a response of type Message
        //extract result from response


        return executorService.submit(() -> {
            Message request = new Message(HelloService.SAY_HELLO, name);

            Message response = tcpClient.sendAndReceive(request);

            String result = response.getBody();
            //todo: handle exceptions e.g. status error

            return result;
        });
    }

    @Override
    public Future<String> sayGoodbye(String name) {
        return executorService.submit(() -> {
            Message request = new Message(HelloService.SAY_GOODBYE, name);

            Message response = tcpClient.sendAndReceive(request);

            String result = response.getBody();
            //todo: handle exceptions e.g. status error

            return result;
        });
    }
}