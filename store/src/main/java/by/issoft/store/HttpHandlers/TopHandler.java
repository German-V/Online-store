package by.issoft.store.HttpHandlers;

import by.issoft.store.XML.Sort;
import by.issoft.store.XML.SortType;
import by.issoft.store.XML.XMLParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.SneakyThrows;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class TopHandler implements HttpHandler {
    @SneakyThrows
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StringBuilder builder = new StringBuilder();
        try {
            XMLParser.parse();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        Sort.setSortMap(Map.of("price", SortType.desc));
        ResultSet rs = Sort.sort();
        Sort.setSortMap(new LinkedHashMap<>());

        for(int i =0; i<5;i++) {
            rs.next();
            String name = rs.getObject(2).toString();
            String price = rs.getObject(3).toString();
            String rate = rs.getObject(4).toString();
            builder.append("<p>").append(name).append(" "+price).append(" " + rate).append("</p>");
        }
        byte[] bytes = builder.toString().getBytes();
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
