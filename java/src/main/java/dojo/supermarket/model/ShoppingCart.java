package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return Collections.unmodifiableList(items);
    }

    void addItem(Product product) {
        addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return Collections.unmodifiableMap(productQuantities);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product product: productQuantities().keySet()) {
            double quantity = productQuantities.get(product);
            if (offers.containsKey(product)) {
                Offer offer = offers.get(product);
                double unitPrice = catalog.getUnitPrice(product);
                int quantityAsInt = (int) quantity;
                Discount discount = null;
                int numOfBoughtItems = 1;
                if (offer.offerType == SpecialOfferType.THREE_FOR_TWO) {
                    numOfBoughtItems = 3;

                } else if (offer.offerType == SpecialOfferType.TWO_FOR_AMOUNT) {
                    numOfBoughtItems = 2;
                    if (quantityAsInt >= numOfBoughtItems) {
                        double total = offer.argument * (quantityAsInt / numOfBoughtItems) + quantityAsInt % numOfBoughtItems * unitPrice;
                        double discountN = unitPrice * quantity - total;
                        discount = new Discount(product, numOfBoughtItems+" for " + offer.argument, -discountN);
                    }

                } if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT) {
                    numOfBoughtItems = 5;
                }
                int numberOfXs = quantityAsInt / numOfBoughtItems;
                if (offer.offerType == SpecialOfferType.THREE_FOR_TWO && quantityAsInt >= numOfBoughtItems) {
                    double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % numOfBoughtItems * unitPrice);
                    discount = new Discount(product, numOfBoughtItems+" for 2", -discountAmount);
                }
                if (offer.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
                    discount = new Discount(product, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100);
                }
                if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT && quantityAsInt >= numOfBoughtItems) {
                    double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % numOfBoughtItems * unitPrice);
                    discount = new Discount(product, numOfBoughtItems + " for " + offer.argument, -discountTotal);
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }
        }
    }
}
