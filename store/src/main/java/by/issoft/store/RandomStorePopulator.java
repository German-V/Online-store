package by.issoft.store;

import by.issoft.domain.*;
import by.issoft.domain.category.CategoryName;
import com.github.javafaker.Faker;
import org.apache.log4j.BasicConfigurator;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class RandomStorePopulator {
    private Faker faker = new Faker();

    public RandomStorePopulator() {
    }

    public String generateProductName(CategoryName Category) {
        return switch (Category) {
            case MILK_CATEGORY -> faker.beer().name();
            case PHONE_CATEGORY -> faker.food().fruit();
            case BIKE_CATEGORY -> faker.dog().name();
        };
    }

    public double generateProductRate() {
        return faker.number().randomDouble(2, 0, 500);
    }

    public double generateProductPrice() {
        return faker.number().randomDouble(2, 0, 500);
    }

    /*static by.issoft.store.Store populateTheStore(int products_number)
    {
        by.issoft.store.RandomStorePopulator populator = new by.issoft.store.RandomStorePopulator();
        by.issoft.store.Store store = new by.issoft.store.Store();
        Faker faker = new Faker();
        List<Category> tempList1 = new ArrayList<>();
        tempList1.add(new MilkCategory());
        tempList1.add(new BikeCategory());
        tempList1.add(new PhoneCategory());
        int temp;
        for (int i = 0; i < products_number; i++) {
            temp = faker.number().numberBetween(0,2);
            tempList1.get(temp).addProduct(
                    new Product(
                            populator.generateProductName(tempList1.get(temp).getName()),
                            populator.generateProductRate(),
                            populator.generateProductPrice()
                    )
            );
        }
        store.setCategoryList(tempList1);
        return store;
    }*/
    public static Map<Category, Integer> createProductListToAdd() {
        Map<Category, Integer> productsToAdd = new HashMap<>();
        BasicConfigurator.configure();

        Reflections reflections = new Reflections("by.issoft.domain", new SubTypesScanner());
        //Get all existed subtypes of by.issoft.domain.Category
        Set<Class<? extends Category>> subTypes = reflections.getSubTypesOf(Category.class);

        //Create a random number of random products for each category
        for (Class<? extends Category> type : subTypes) {
            try{
                Random random = new Random();
                productsToAdd.put(type.getConstructor().newInstance(), random.nextInt(50));
            } catch (InvocationTargetException
                    | InstantiationException
                    | IllegalAccessException
                    | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return productsToAdd;
    }

}
