package dojo.supermarket.model;

import java.util.HashMap;
import java.util.Map;

public class SupermarketCatalog {
    private Map<String, Double> prices = new HashMap<>();

    public void addProduct(Product product, double price) {
        this.prices.put(product.getName(), price);
    }

    public double getUnitPrice(Product product) {
        return this.prices.get(product.getName());
    }


}
