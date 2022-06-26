package by.issoft.store;

import by.issoft.store.HttpHandlers.*;
import com.sun.net.httpserver.*;
import lombok.SneakyThrows;
import java.net.*;

public class HTTP {

    @SneakyThrows
    public void createServer() {

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/", new InfoHandler());
        httpServer.createContext("/products", new ProductsHandler());
        httpServer.createContext("/sorted", new SortHandler());
        httpServer.createContext("/top", new TopHandler());

        httpServer.createContext("/order", new OrderHandler())
                .setAuthenticator(new BasicAuthenticator("get") {
            @Override
            public boolean checkCredentials(String username, String password) {
                return username.equals("user1") && password.equals("password");
            }
        });

        //context.setAuthenticator(new Auth());

        httpServer.setExecutor(null);
        httpServer.start();
    }

}