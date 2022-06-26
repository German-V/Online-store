package by.issoft.store.XML;

import by.issoft.domain.Product;
import by.issoft.store.Database;
import lombok.SneakyThrows;

//import org.reflections.by.issoft.store.Store;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.issoft.store.Store.getCategoriesList;

public class Sort //implements Comparable
{
    private static Map<String, SortType> sortMap = new LinkedHashMap<>();

    public static Map<String, SortType> getSortMap() {
        return sortMap;
    }

    public static void setSortMap(Map<String, SortType> sortMap) {
        Sort.sortMap = sortMap;
    }

    @SneakyThrows
    public static ResultSet sort() {
        Statement statement = Database.statement;
        ResultSet rs = null;
        List<String> categories = getCategoriesList();

        StringBuilder statementString = new StringBuilder();
        if (!categories.isEmpty()) statementString.append(categories.get(0));
        for (int i = 1; i < categories.size(); i++) {
            statementString.append(" union select * from " + categories.get(i));
        }
        String resStatement = statementString.toString();
        for (Map.Entry<String, SortType> entry : sortMap.entrySet()) {
            rs = statement.executeQuery("select * from " + resStatement +
                                        " order by " + entry.getKey() + " " + entry.getValue().name());

        }
        return rs;

    }
}