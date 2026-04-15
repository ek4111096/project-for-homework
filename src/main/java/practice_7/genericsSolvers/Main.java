package practice_7.genericsSolvers;

import practice_7.Storage;

public class Main {
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }

    public static void main(String[] args) {
        String[] words = {"Apple", "Banana"};
        Integer[] nums = {1, 50, 60};
        printArray(words);
        printArray(nums);

        Pair<String, String> pair = new Pair<>();
        pair.setElementT("elementT");
        pair.setElementU("elementU");
        System.out.println(pair.getElementT());
        System.out.println(pair.getElementU());

        Storage<String> storage = new Storage<>("firstElement");
        System.out.println(storage.getElement());

        Box<String> stringBox = new Box<>();
        stringBox.setElement("FirstElement");
        System.out.println(stringBox.getElement());

    }
}
