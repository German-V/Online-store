package by.issoft.domain;

import java.util.Objects;

public class Product {
    private final String name;
    private double price;
    private double rate;

    public Product(String name, double price, double rate) {
        this.name = name;
        this.price = price;
        this.rate = rate;
    }

    @Override
    public boolean equals(Object obj) {
        Product product = (Product) obj;
        return Objects.equals(getName(), product.getName())
                && getPrice() == product.getPrice()
                && getRate() == product.getRate();

       /* if(getName().equals(product.getPrice())
        || getPrice() == product.getPrice()
        && getRate() == product.getRate())
            return true;
        else return false;*/
       // return super.equals(obj);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getRate() {
        return rate;
    }
}
