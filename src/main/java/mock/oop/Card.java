package mock.oop;

public class Card extends Payment{

    private String holder;
    private int amount;

    public Card(String holder, int amount) {
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
        System.out.println("Карта " + holder + " оплатила " + amount);
    }
}
