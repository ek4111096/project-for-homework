package practice_6.TreeMapSolvers;

import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        //1
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("Zoya", 9);
        map.put("Galina", 10);
        map.put("Marina", 4);
        map.put("Larisa", 7);
        map.put("Alla", 5);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        //2
        System.out.println(map.firstKey());
        System.out.println(map.lastKey());

        //3
        TreeMap<Integer, String> map1 = new TreeMap<>();
        map1.put(123, "Tolya");
        map1.put(456, "Alina");
        map1.put(789, "Zarina");
        Integer id = 300;
        System.out.println(map1.higherKey(id));
    }
}
