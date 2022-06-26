package by.issoft.store;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    static Connection connection;
    public static Statement statement;
    @SneakyThrows
    public static void start(){
        connection = DriverManager.getConnection("jdbc:h2:~/storeDB");

        System.out.println("connected");
        statement = connection.createStatement();
    }
    @SneakyThrows
    public static void stopConnection(){
        //statement.execute("drop table categories");
        connection.close();
        System.out.println("disconnected");
    }
}
