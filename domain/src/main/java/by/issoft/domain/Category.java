package by.issoft.domain;

import by.issoft.domain.category.CategoryName;

import java.util.ArrayList;
import java.util.List;

public class Category {
    CategoryName name;
    private List<Product> ProductList;

    public Category(CategoryName name) {
        this.name = name;
        ProductList = new ArrayList<>();
    }

    public CategoryName getName() {
        return name;
    }

    public List<Product> getProductList() {
        return ProductList;
    }

    public void setProductList(List<Product> productList) {
        ProductList = productList;
    }

    public void addProduct(Product product) {
        ProductList.add(product);
    }

    public void removeProduct(int index) {

    }
}
