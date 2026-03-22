package practice_6.LinkedListSolutions;

import java.util.LinkedList;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) {
        //1 task
        LinkedList<String> list = new LinkedList<>();
        list.add("Aa");
        list.add("Bb");
        list.add("Cc");
        list.add("Dd");
        list.add("Ee");

        System.out.println(list);

        //2 task
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            queue.addLast(i);
        }
        System.out.println(queue);
        while (!queue.isEmpty()) {
            queue.poll();
            System.out.println(queue);
        }

        //5 task
        LinkedList<String> list1 = new LinkedList<>();
        list1.add("A");
        list1.add("B");
        list1.add("C");

        ListIterator<String> it = list1.listIterator();
        while (it.hasNext()) {
            String element = it.next();
            System.out.println(element);
        }

        while (it.hasPrevious()) {
            String element = it.previous();
            System.out.println(element);
        }
    }
}
