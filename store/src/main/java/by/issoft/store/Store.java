package by.issoft.store;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import lombok.Data;
import lombok.SneakyThrows;


import java.sql.Array;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static by.issoft.store.Database.*;

public class Store {

    public static Store store;

    //private List<Category> CategoryList;

    //private List<Product> orderList;

    private Store() {
        //CategoryList = new ArrayList<>();
        //orderList = new ArrayList<>();
    }

    public static Store getStore()
    {
        if(store==null)
        {
            store = new Store();
        }
        return store;
    }


    @SneakyThrows
    public static List<String> getCategoriesList(){
        Statement statement = Database.statement;
        ResultSet rs1 = statement.executeQuery("SELECT * FROM CATEGORIES");

        List<String> categories = new ArrayList<>();
        while (rs1.next()) {
            categories.add(rs1.getObject(2).toString());
        }
        return categories;
    }
    @SneakyThrows
    public void printStore(Store store) {
        //Statement statement = Database.connection.prepareStatement("SELECT * FROM MILK_CATEGORY");
        Statement statement = Database.statement;
        ResultSet rs;
        List<String> categories = getCategoriesList();
        for (String currentCategory:categories){


            //String currentCategory = rs1.getObject(2).toString();
            System.out.println("\n"+currentCategory+"\n");
            rs = statement.executeQuery("SELECT * FROM " + currentCategory);
            while (rs.next()) {
                String name = rs.getObject(2).toString();
                String price = rs.getObject(3).toString();
                String rate = rs.getObject(4).toString();
                System.out.println(name + " price: " + price + " rate: " + rate);
            }
        }

    }

    @SneakyThrows
    public void fillTheStore(Map<Category, Integer> categoryMap)
    {
        RandomStorePopulator populator = new RandomStorePopulator();

        Statement tempStatement = statement;
        tempStatement.execute("drop table if exists categories");
        tempStatement.execute("""
                CREATE TABLE IF NOT EXISTS Categories
                            (
                                    Id INT AUTO_INCREMENT,
                                    Name ENUM('MILK_CATEGORY', 'BIKE_CATEGORY', 'PHONE_CATEGORY') PRIMARY KEY,
                                    Amount INT
                            );""");
        for (Map.Entry<Category, Integer> category: categoryMap.entrySet()){
            tempStatement.execute("INSERT INTO Categories(Name, Amount)\n"
            +"VALUES(\'" + category.getKey().getName()+ "\', "+ category.getValue() +")");
            tempStatement.execute("drop table if exists " + category.getKey().getName());

            tempStatement.execute("CREATE TABLE IF NOT EXISTS "+category.getKey().getName()+"\n" +
                              "            (\n" +
                              "                    Id INT AUTO_INCREMENT,\n" +
                              "                    Name VARCHAR,\n" +
                              "                    Price DOUBLE,\n" +
                              "                    Rate DOUBLE,\n" +
                              "                    Type CHAR(20) DEFAULT '"+category.getKey().getName()+"'\n" +
                              "            );");

            for (int i = 0; i < category.getValue(); i++) {
                tempStatement.execute("INSERT INTO "+ category.getKey().getName()+" (Name, Price, Rate)\n"+
                                      "VALUES('"
                                      +populator.generateProductName(category.getKey().getName())+ "', "
                                      +populator.generateProductRate()+", "
                                      +populator.generateProductPrice()
                                      + " );");
            }
        }

    }

}
