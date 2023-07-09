package ro.ubb.socket.client.tcp;

import ro.ubb.socket.common.HelloService;
import ro.ubb.socket.common.HelloServiceException;
import ro.ubb.socket.common.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class TcpClient {
    private ExecutorService executorService;

    public TcpClient(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Message sendAndReceive(Message request) {
        try (var socket = new Socket(Message.HOST, Message.PORT);
             var is = socket.getInputStream();
             var os = socket.getOutputStream()) {

            //System.out.println("sending request: " + request);
            request.writeTo(os);
            //System.out.println("request sent");

            Message response = new Message();
            response.readFrom(is);
            //System.out.println("received response: " + response);

            return response;

        } catch (IOException e) {
            e.printStackTrace();
            throw new HelloServiceException("exception in send and receive", e);
        }

    }
}