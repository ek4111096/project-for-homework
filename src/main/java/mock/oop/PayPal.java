package mock.oop;

public class PayPal extends Payment{
    private String holder;
    private int amount;

    public PayPal(String holder, int amount) {
        this.holder = holder;
        this.amount = amount;
    }

    public String getHolder() {
        return holder;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public void makePayment() {
        System.out.println("PayPal " + holder + " оплатил " + amount);
    }
}
