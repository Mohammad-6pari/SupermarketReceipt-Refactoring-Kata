package dojo.supermarket.model;

public class Offer {

    SpecialOfferType offerType;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }
    public Discount handleDiscount(double quantity , double unitPrice){
        int quantityAsInt = (int) quantity;
        int boughtItems = 1;
        if (this.offerType == SpecialOfferType.THREE_FOR_TWO) {
            boughtItems = 3;

        } else if (this.offerType == SpecialOfferType.TWO_FOR_AMOUNT) {
            boughtItems = 2;
            if (quantityAsInt >= boughtItems) {
                double total = this.argument * (quantityAsInt / boughtItems) + quantityAsInt % boughtItems * unitPrice;
                double discountN = unitPrice * quantity - total;
                return new Discount(product, boughtItems+" for " + this.argument, -discountN);
            }

        } if (this.offerType == SpecialOfferType.FIVE_FOR_AMOUNT) {
            boughtItems = 5;
        }
        int numOfBoughtItems = quantityAsInt / boughtItems;
        if (this.offerType == SpecialOfferType.THREE_FOR_TWO && quantityAsInt >= boughtItems) {
            double discountAmount = quantity * unitPrice - ((numOfBoughtItems * 2 * unitPrice) + quantityAsInt % boughtItems * unitPrice);
            return new Discount(product, boughtItems+" for 2", -discountAmount);
        }
        if (this.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
            return new Discount(product, this.argument + "% off", -quantity * unitPrice * this.argument / 100);
        }
        if (this.offerType == SpecialOfferType.FIVE_FOR_AMOUNT && quantityAsInt >= boughtItems) {
            double discountTotal = unitPrice * quantity - (this.argument * numOfBoughtItems + quantityAsInt % boughtItems * unitPrice);
            return new Discount(product, boughtItems + " for " + this.argument, -discountTotal);
        }
        return null;

    }

    Product getProduct() {
        return product;
    }
}
