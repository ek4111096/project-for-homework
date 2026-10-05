package programmingorinciples;

public class KissViolationSolution {
    public double calculateDiscount(double price, boolean isLoyalCustomer, boolean isFirstPurchase, boolean hasCoupon) {

        double discount = 0.0;

        if (isLoyalCustomer && isFirstPurchase) {
            discount = price * 0.10;
        } else if (isLoyalCustomer) {
            discount = price * 0.05;
        } else if (hasCoupon) {
            discount = price * 0.07;
        } else {
            discount = price * 0.02;
        }

        return price - discount;
    }
}
