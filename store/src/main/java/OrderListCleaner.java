import by.issoft.store.Database;
import by.issoft.store.Store;
import lombok.SneakyThrows;

import java.util.TimerTask;

public class OrderListCleaner extends TimerTask {
    @SneakyThrows
    @Override
    public void run() {
        Database.statement.execute("TRUNCATE TABLE orders");
        System.out.println("Cleaned!");
    }
}
