package catalog.client;

import catalog.client.ui.ClientConsole;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.security.KeyStoreException;

public class ClientApp {
    public static void main(String[] args) throws KeyStoreException, NoSuchFieldException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("catalog.client.config");
//
        System.out.println("BEANS : ");
//
        String[] beans = context.getBeanDefinitionNames();
//
        for(String bn: beans)
            System.out.println(bn);
//
        System.out.println("END BEANS");

        ClientConsole console = context.getBean(ClientConsole.class);
        console.runConsole();
        System.out.println("bye ");
    }
}
