package mock.oop;

public class Crypto extends Payment{
    private String holder;
    private int amount;

    public Crypto(String holder, int amount) {
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
        System.out.println("Криптокошелек " + holder + " отправил " + amount);
    }
}
