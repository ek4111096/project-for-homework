package programmingorinciples;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private List<Sendable> senders = new ArrayList<>();
    public void addSender(Sendable sender) {
        senders.add(sender);
    }
    public void sendNotification(String message) {
        for (Sendable sender : senders) {
            sender.send(message);
        }
    }
}
interface Sendable {
    public void send(String message);
}

class EmailSender implements Sendable {
    @Override
    public void send(String message) {
        System.out.println("Отправка email: " + message);
    }
}
