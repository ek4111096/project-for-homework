package clean_code.builder.order;

public class Main {
    public static void main(String[] args) {
        Order order = new Order.Builder()
                .setItem("Laptop")
                .setDiscount(10.0)
                .setPaymentType("Card")
                .build();

        System.out.println(order);
    }
}
