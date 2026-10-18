package mock.alg;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * добавить элементы через offer
 * удалять через poll
 *
 */

public class Main {


    public static void main(String[] args) {
        OperatiomsForQueue queue = new OperatiomsForQueue();
        queue.offerElement("Hello");
        System.out.println(queue.getQueue().peek());
        queue.pollElement("Hello");
        System.out.println(queue.getQueue().size());

    }
}


