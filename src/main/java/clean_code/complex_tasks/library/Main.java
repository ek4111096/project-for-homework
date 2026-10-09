package clean_code.complex_tasks.library;

public class Main {


    public static void main(String[] args) {
        Book book = new Book.BookBuilder()
                .setTitle("Война и мир")
                .setAuthor("Лев Толстой")
                .setContent("Содержание книги...")
                .build();

        BookProxy proxy = new BookProxy(book);

        System.out.println(proxy.getContent());
        System.out.println(proxy.getContent());
    }
}
