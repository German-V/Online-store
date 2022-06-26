package by.issoft.store.HttpHandlers;

import by.issoft.store.Database;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static by.issoft.store.Database.statement;
import static by.issoft.store.Store.getCategoriesList;

public class ProductsHandler implements HttpHandler {


    @SneakyThrows
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        StringBuilder builder = new StringBuilder();
        Statement statement = Database.statement;
        ResultSet rs;
        List<String> categories = getCategoriesList();
        for (String currentCategory:categories){
            builder.append("<p>").append(currentCategory).append("</p>");
            rs = statement.executeQuery("SELECT * FROM " + currentCategory);
            while (rs.next()) {
                String name = rs.getObject(2).toString();
                String price = rs.getObject(3).toString();
                String rate = rs.getObject(4).toString();
                builder.append("<p>").append(name).append(" " + price).append(" "+ rate).append("</p>");
            }
        }
        byte[] bytes = builder.toString().getBytes();
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
