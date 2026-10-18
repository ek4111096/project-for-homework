package mock.alg;

import java.util.PriorityQueue;
import java.util.Queue;

public class OperatiomsForQueue {
    public Queue<String> queue = new PriorityQueue<>();
    public void offerElement(String s) {
        queue.offer(s);
    }


    public void pollElement(String s) {
        queue.poll();
    }

    public Queue<String> getQueue() {
        return queue;
    }
}
