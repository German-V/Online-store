package by.issoft.store.HttpHandlers;

import by.issoft.domain.Product;
import by.issoft.store.Database;
import by.issoft.store.Store;
import com.github.javafaker.Faker;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class OrderHandler implements HttpHandler {
    Faker faker;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
            createOrdersTable();
            orderCommand(exchange);

    }
    public void orderCommand(HttpExchange exchange){
        faker = new Faker();
        Queue<Product> products = new LinkedList<>();
        Thread producer = new Thread(new Producer(products));
        Thread consumer = new Thread(new Consumer(products));
        producer.start();
        consumer.start();

        System.out.println("Print \"stop\" to stop ordering");
        Scanner in = new Scanner(System.in);
        while(!in.nextLine().equals("stop")) System.out.println("Wrong");
        Producer.stopRunning();
        Consumer.stopRunning();
        ordersOutput(exchange);

    }
    @SneakyThrows
    public static void createOrdersTable(){
        Statement tempStatement = Database.statement;
        tempStatement.execute("drop table if exists orders");
        tempStatement.execute("""
                CREATE TABLE IF NOT EXISTS orders
                            (
                                    Id INT AUTO_INCREMENT,
                                    Name VARCHAR PRIMARY KEY,
                                    Price DOUBLE,
                                    rate DOUBLE
                            );""");
    }
    @SneakyThrows
    public void ordersOutput(HttpExchange exchange){
        StringBuilder builder = new StringBuilder();
        System.out.println("Please wait");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("Your orders");
        ResultSet rs;
        rs = Database.statement.executeQuery("SELECT * FROM orders");
        builder.append("<p>").append("Your orders:").append("</p>");
        while (rs.next()) {
            String name = rs.getObject(2).toString();
            String price = rs.getObject(3).toString();
            String rate = rs.getObject(4).toString();
            builder.append("<p>").append(name).append(" " + price).append(" "+ rate).append("</p>");
        }
        byte[] bytes = builder.toString().getBytes();
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

}

class Producer extends  Thread{
    final Queue<Product> product;
    List<String> allCategories;
    static boolean running = true;
    public Producer(Queue<Product> product){
        this.product = product;
        allCategories = Store.getCategoriesList();
        //this.allProducts = allProducts;
    }
    @SneakyThrows
    @Override
    public void run() {
        while(running){
            synchronized (product)
            {
                //product.add(allProducts.get(new Faker().number().numberBetween(0,allProducts.size())));
                product.add(generateProduct());
                System.out.println("Your request is proceeding");
                product.notify();
            }
            TimeUnit.SECONDS.sleep(new Faker().number().numberBetween(1,30));
            System.out.println("Done!");
        }
    }
    @SneakyThrows
    public Product generateProduct(){
        Faker faker = new Faker();

        String category = allCategories.get(faker.number().numberBetween(0,allCategories.size()-1));
        ResultSet rs1 = Database.statement.executeQuery("select amount from categories where name = '"+
                category +"'");
        rs1.next();
        int id = faker.number().numberBetween(1, rs1.getInt(1));
        ResultSet st = Database.statement.executeQuery("select * from "+ category + " where id = " + id);
        st.next();
        //String name =
        return new Product(st.getObject(2).toString(), st.getDouble(3), st.getDouble( 4));
    }
    public static void stopRunning(){
        running = false;
    }
}
class Consumer extends Thread{
    final Queue<Product> product;
    //List<Product> ordersList;
    static boolean running = true;
    public Consumer(Queue<Product> product){
        this.product = product;
        //this.ordersList = ordersList;
    }
    @SneakyThrows
    @Override
    public void run() {
        while(running) {
            synchronized (product)
            {
                if(product.isEmpty())
                    product.wait();
                Product tempProduct = product.remove();
                Database.statement.execute("INSERT INTO ORDERS(NAME, PRICE, RATE)\n" +
                        "VALUES ( '" + tempProduct.getName()+"' , "+ tempProduct.getPrice()
                        + " ," + tempProduct.getRate()+" )");
                TimeUnit.SECONDS.sleep(1);
            }
        }
        System.out.println("Stopped!");
    }
    public static void stopRunning(){
        running = false;
    }
}
