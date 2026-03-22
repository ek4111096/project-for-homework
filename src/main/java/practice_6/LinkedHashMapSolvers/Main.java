package practice_6.LinkedHashMapSolvers;

import java.util.LinkedHashMap;

public class Main {
    public static void addPage(LinkedHashMap<String, String> history, String  id, String page) {
        if (history.size() < 10) {
            history.put(id, page);
        } else {
            history.pollFirstEntry();
            history.put(id, page);
        }
        System.out.println(history.entrySet());
    }
    public static void main(String[] args) {
        //1
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("Sasha", 20);
        map.put("Masha", 19);
        map.put("Dasha", 21);
        map.put("Pasha", 18);
        map.put("Katya", 19);
        map.forEach((name, age) ->
                System.out.println(name + " - " + age)
        );

        //2
        LinkedHashMap<String, Integer> phoneBook = new LinkedHashMap<>();
        phoneBook.put("Pasha", 123);
        phoneBook.put("Masha", 456);
        phoneBook.put("Dasha", 789);
        String name = "kasha";
        if (phoneBook.containsKey(name)) {
            Integer number = phoneBook.get(name);
            System.out.println(name + " - " + number);
        } else {
            System.out.println("Contact not found");
        }

        //3
        LinkedHashMap<String, String> history = new LinkedHashMap<>();
        String id = "120";
        String id1 = "121";
        String id2 = "122";
        String id3 = "123";
        String id4 = "124";
        String id5 = "125";
        String id6 = "126";
        String id7 = "127";
        String id8 = "128";
        String id9 = "129";
        String id10 = "130";
        String page = "home";
        addPage(history, id, page);
        addPage(history, id1, page);
        addPage(history, id2, page);
        addPage(history, id3, page);
        addPage(history, id4, page);
        addPage(history, id5, page);
        addPage(history, id6, page);
        addPage(history, id7, page);
        addPage(history, id8, page);
        addPage(history, id9, page);
        addPage(history, id10, page);



    }
}
