//package chain_of_responsibility;

/*import by.issoft.domain.Product;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopHandlerTest {
    public static List<Product> testList;

    public static List<Product> expectedList;

    public static TopHandler testTopHandler;

    @BeforeAll
    static void beforeAll() {
        testList = new ArrayList<>();
        testList.add(new Product("a", 23 ,91));
        testList.add(new Product("b", 13 ,9));
        testList.add(new Product("c", 3 ,910));
        testList.add(new Product("d", 2 ,1));
        testList.add(new Product("e", 1 ,1));
        testList.add(new Product("f", 11 ,9));

        expectedList = new ArrayList<>();
        testList.add(new Product("a", 23 ,91));
        testList.add(new Product("b", 13 ,9));
        testList.add(new Product("f", 11 ,9));
        testList.add(new Product("c", 3 ,910));
        testList.add(new Product("d", 2 ,1));

        testTopHandler = new TopHandler();
    }

    @Test
    void topCommand() {
        testList = testTopHandler.topCommand();
    }
}*/