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
        int numOfBoughtItems = 1;
        int quantityBoughtRatio = quantityAsInt / numOfBoughtItems;
        if (this.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
            return new Discount(product, this.argument + "% off", -quantity * unitPrice * this.argument / 100);
        }
        else {
            double offerAmount = this.argument;
            double priceCoef = unitPrice;
            if (this.offerType == SpecialOfferType.THREE_FOR_TWO) {
                numOfBoughtItems = 3;
                offerAmount = 2;
            }
            else if (this.offerType == SpecialOfferType.TWO_FOR_AMOUNT) {
                numOfBoughtItems = 2;
                priceCoef = 1;
            }
            else if (this.offerType == SpecialOfferType.FIVE_FOR_AMOUNT) {
                numOfBoughtItems = 5;
                priceCoef = 1;
            }
            if (quantityAsInt >= numOfBoughtItems) {
                double discountTotal = unitPrice * quantity - (offerAmount * quantityBoughtRatio * priceCoef + quantityAsInt % numOfBoughtItems * unitPrice);
                return new Discount(product, numOfBoughtItems + " for " + offerAmount, -discountTotal);
            }
        }
        return null;

    }

    Product getProduct() {
        return product;
    }
}
