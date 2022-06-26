import by.issoft.store.Database;
import by.issoft.store.HTTP;
import by.issoft.store.RandomStorePopulator;
import by.issoft.store.Store;

import java.util.Timer;

public class StoreApp {


    public static void main(String[] args) {
        Store store = Store.getStore();
        HTTP http = new HTTP();
        Database.start();
        store.fillTheStore(RandomStorePopulator.createProductListToAdd());
        http.createServer();
        Timer timer = new Timer();
        timer.schedule(new OrderListCleaner(),0, 120_000);
    }


}
