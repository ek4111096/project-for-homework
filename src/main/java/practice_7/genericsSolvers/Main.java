package practice_7.genericsSolvers;

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

        Pair pair = new Pair();
        pair.setElementT("Smth");
        System.out.println(pair.getElementT());
    }
}
