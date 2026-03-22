package practice_6.ArrayListSolutions;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        //1 task
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            nums.add(i);
        }
        nums.addLast(25);
        System.out.println(nums);


        //2 task
        ArrayList<Integer> evenNums = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            evenNums.add(random.nextInt(100));
        }
        System.out.println(evenNums);
        for (int i = 0; i < evenNums.size(); i++) {
            if (evenNums.get(i) % 2 == 0) {
                System.out.println(evenNums.get(i));
            }
        }

        //3 task
        ArrayList<String> list = new ArrayList<>();
        list.add("Aaa");
        list.add("Aaaaaa");
        list.add("Aaaa");

        AtomicInteger max = new AtomicInteger();
        list.forEach(
                word -> {
                    if (word.length() > max.get()) {
                        max.set(word.length());
                    }
                }
        );
        list.forEach(
                word -> {
                    if (word.length() == max.get()) {
                        System.out.println(word);
                    }
                }
        );

        //4 task
        ArrayList<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list1.add(i);
        }
        int sum = 0;
        for (int i = 0; i < list1.size(); i++) {
            sum += list1.get(i);
        }
        System.out.println(sum);

        //5 task
        ArrayList<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list2.add(i);
        }
        int maxResult = list2.get(0);
        for (int i = 1; i < list2.size(); i++) {
            if (maxResult < list2.get(i)) {
                maxResult = list2.get(i);
            }
        }
        System.out.println(maxResult);
    }



}
