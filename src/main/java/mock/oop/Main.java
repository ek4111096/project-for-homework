package mock.oop;

public class Main {
    public static void main(String[] args) {
        Crypto crypto = new Crypto("Ivan", 5000);
        Card card = new Card("Alex", 1200);
        PayPal payPal = new PayPal("Maria", 850);

        Payroll payroll = new Payroll();
        payroll.addPayment(card);
        payroll.addPayment(crypto);
        payroll.addPayment(payPal);

        payroll.payAll();
    }
}
