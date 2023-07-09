package ro.ubb.socket.common;

import java.util.concurrent.Future;

public interface HelloService {
    int PORT = 1234;
    String HOST = "localhost";

    String SAY_HELLO = "sayHello";

    String SAY_GOODBYE = "sayGoodbye";

    Future<String> sayHello(String name);

    Future<String> sayGoodbye(String name);
}