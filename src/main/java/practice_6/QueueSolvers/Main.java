package practice_6.QueueSolvers;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

public class Main {
    public static void addElements(ArrayDeque<Integer> deque, Integer first, Integer last) {
        deque.addFirst(first);
        deque.addLast(last);
        System.out.println(deque);
    }
    public static void removeElements(ArrayDeque<Integer> deque) {
        deque.removeFirst();
        deque.removeLast();
        System.out.println(deque);
    }

    public static void main(String[] args) {

        //priorityQueue
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(10);
        queue.add(8);
        queue.add(1);
        queue.add(9);
        queue.add(88);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }

        //ArrayDeque
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(65);
        deque.add(45);
        deque.add(178);
        deque.add(23);
        deque.add(28);
        System.out.println(deque);

        //2
        ArrayDeque<Integer> deque1 = new ArrayDeque<>();
        deque1.push(6);
        deque1.push(67);
        deque1.push(1);
        deque1.push(23);
        while (!deque1.isEmpty()) {
            System.out.println(deque1.pop());
        }

        //3
        ArrayDeque<Integer> deque2 = new ArrayDeque<>();
        addElements(deque2, 3, 8);
        addElements(deque2, 2, 9);
        addElements(deque2, 1, 10);
        removeElements(deque2);
    }

}
