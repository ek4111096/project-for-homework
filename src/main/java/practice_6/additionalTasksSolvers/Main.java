package practice_6.additionalTasksSolvers;

import java.util.*;

public class Main {
    public static void checkUser(HashMap<Integer, String> map, Integer id) {
        boolean res;
        if (map.containsKey(id)) {
            res = true;
            System.out.println("Пользователь есть в списке");
        } else {
            res = false;
            System.out.println("Пользователя нет в списке");
        }

    }
    public static void queueRealize(LinkedList<String> list) {
        while (!list.isEmpty()) {
            list.poll();
            System.out.println(list);
        }

    }

    public static void main(String[] args) {
        HashMap<Integer, String> users = new HashMap<>();
        users.put(123, "Aa");
        users.put(456, "Bb");
        users.put(789, "Cc");

        checkUser(users, 45);


        /**
         * Хранить задачи в порядке поступления и обрабатывать по очереди.
         *
         */
        LinkedList<String> queue = new LinkedList<>();
        queue.add("A");
        queue.add("B");
        queue.add("C");
        queue.add("Dd");
        System.out.println(queue);
        queueRealize(queue);

        //3
        /**
         * Используй HashMap<String, String>
         * Добавляй параметры через put("имя", "значение")
         * Получай значение по ключу через get("имя")
         * Для проверки существования параметра — containsKey("имя")
         * Хранить параметры приложения по имени и значению.
         */

        HashMap<String, String> settings = new HashMap<>();
        settings.put("home", "default1");
        settings.put("main", "default2");
        settings.put("cards", "default3");
        settings.put("home", "default4");
        System.out.println(settings);
        System.out.println(settings.get("home"));
        System.out.println(settings.containsKey("card"));

        //4
        /**
         * Отслеживать уникальные страницы и общее количество посещений
         *
         */
        Set<String> uniquePages = new HashSet<>();
        int totalVisits = 0;

        totalVisits += visit(uniquePages, "google.com");
        totalVisits += visit(uniquePages, "youtube.com");
        totalVisits += visit(uniquePages, "google.com");

        System.out.println("Unique pages: " + uniquePages);
        System.out.println("Total visits: " + totalVisits);

        //5
        /**
         * Хранить действия пользователя для отмены и повтора
         */
        LinkedList<String> history = new LinkedList<>();
        history.add("1act");
        history.add("2act");
        history.add("3act");
        String lastElement = history.getLast();
        history.add(lastElement);
        System.out.println(history);
        history.pollLast();
        System.out.println(history);

        //6
        /**
         * Хранить имена и номера телефонов, быстро находить номер по имени.
         */
        HashMap<String, Integer> phoneBook = new HashMap<>();
        phoneBook.put("Lena", 123);
        phoneBook.put("Pena", 234);
        phoneBook.put("Gena", 345);
        System.out.println(phoneBook.get("Lena"));

        //7
        /**
         * Определить, правильно ли расставлены скобки.
         * Используй Stack<Character> (можно через ArrayDeque)
         * Проходи по строке посимвольно
         * Открывающие скобки добавляй в стек
         * При встрече закрывающей — проверяй соответствие верхнему элементу стека
         * В конце стек должен быть пуст
         * Поддержи типы: (), {}, []


        //ArrayList<Character> braces = new ArrayList<Character>(Arrays.asList("{", "}", "(", "["));
        String string = "[{}()]";
        ArrayDeque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if ((ch == "{") || (ch == "(") ||(ch == "[")) {
                stack.add(ch);
            }
            if ((ch == "}") || (ch == ")") ||(ch == "]")) {
                if (!stack.isEmpty()) {
                    System.out.println("Wrong");
                } else {
                    stack.removeLast();
                }
            }
        }
        System.out.println(stack.size());
         */
        String str = "[{}()]";

        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put('}', '{');
        pairs.put(']', '[');

        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (char ch : str.toCharArray()) {

            // если закрывающая скобка
            if (pairs.containsKey(ch)) {

                if (stack.isEmpty() || stack.pop() != pairs.get(ch)) {
                    System.out.println("Wrong");
                    return;
                }

            } else {
                // открывающая
                stack.push(ch);
            }
        }

        System.out.println(stack.isEmpty() ? "Correct" : "Wrong");

        //8
        /**
         * Уникальные слова в тексте
         * Задача:
         * Получить все уникальные слова и их количество.
         * Раздели текст на слова через split(" ")
         * Используй HashSet<String> для хранения слов
         * Каждое слово добавляй в set
         * В конце: set.size() — количество уникальных слов
         */

        String text = "Используйте ArrayDeque как стек: добавьте элементы и извлеките их в обратном порядке. Используйте ArrayDeque как стек: добавьте элементы и извлеките их в обратном порядке.";

        String[] words = text.split(" ");

        HashSet<String> wordsUnique = new HashSet<>();

        for (int i = 0; i < words.length; i++) {
            wordsUnique.add(words[i]);
        }

        System.out.println("Unique words: " + wordsUnique);
        System.out.println("Count: " + wordsUnique.size());
    }

    public static int visit(Set<String> set, String page) {
        set.add(page); // добавит только если уникальная
        return 1; // каждый вызов = одно посещение



    }

}
