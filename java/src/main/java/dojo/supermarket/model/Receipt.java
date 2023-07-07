package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receipt {

    private final List<ReceiptItem> items = new ArrayList<>();
    private final List<Discount> discounts = new ArrayList<>();

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (ReceiptItem item : items) {
            totalPrice += item.getTotalPrice();
        }
        for (Discount discount : discounts) {
            totalPrice += discount.getDiscountAmount();
        }
        return totalPrice;
    }

    public void addProduct(Product product, double quantity, double price, double totalPrice) {
        items.add(new ReceiptItem(product, quantity, price, totalPrice));
    }

    public List<ReceiptItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
