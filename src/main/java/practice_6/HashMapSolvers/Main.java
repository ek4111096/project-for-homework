package practice_6.HashMapSolvers;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        //1
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Ivan", 10);
        map.put("Petr", 15);
        map.put("Vasya", 18);
        map.put("Dima", 17);
        map.put("Ruslan", 16);
        map.forEach(( name, age) ->
                System.out.println(name + " - " + age)
        );

        //2
        String name1 = "Lena";
        if (map.containsKey(name1)) {
            System.out.println(name1 + " exists");
        } else {
            System.out.println(name1 + " not found");
        }

        //3
        map.forEach((name, age) -> {
            if (age < 18) {
                System.out.println(name + " - underage");
            }
        });

    }
}
