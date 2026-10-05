package clean_code.builder.order;

public class Order {
    private String item;
    private double discount;
    private String paymentType;

    public Order(String item, double discount, String paymentType) {
        this.item = item;
        this.discount = discount;
        this.paymentType = paymentType;
    }

    public Order(Builder builder) {
        this.discount = builder.discount;
        this.paymentType = builder.paymentType;
        this.item = builder.item;
    }

    @Override
    public String toString() {
        return "Order{" +
                "item='" + item + '\'' +
                ", discount=" + discount +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }

    static class Builder {

        private String item;
        private double discount;
        private String paymentType;

        public Builder setItem(String item) {
            this.item = item;
            return this;
        }

        public Builder setDiscount(double discount) {
            this.discount = discount;
            return this;
        }

        public Builder setPaymentType(String paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
