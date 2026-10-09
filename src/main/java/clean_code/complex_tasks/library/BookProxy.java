package clean_code.complex_tasks.library;

public class BookProxy implements BookLoader {
    private final Book book;
    private String content;

    public BookProxy(Book book) {
        this.book = book;
    }

    public String getAuthor() {
        return book.getAuthor();
    }

    public String getDescription() {
        return book.getDescription();
    }

    public String getCover() {
        return book.getCover();
    }

    public String getTitle() {
        return book.getTitle();
    }

    @Override
    public String loadContent(Book book) {
        System.out.println("Загружаем содержание книги: " + book.getTitle());
        return book.getContent();
    }

    public String getContent() {
        if (content == null) {
            content = loadContent(book);
        }
        return content;
    }
}
