package clean_code.complex_tasks.library;

public class Book {
    private final String title;
    private final String author;
    private final String description;
    private final String cover;
    private final String content;

    private Book(BookBuilder bookBuilder) {
        this.title = bookBuilder.title;
        this.author = bookBuilder.author;
        this.description = bookBuilder.description;
        this.cover = bookBuilder.cover;
        this.content = bookBuilder.content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getCover() {
        return cover;
    }

    public String getContent() {
        return content;
    }

    public static class BookBuilder {
        private String title;
        private String author;
        private String description;
        private String cover;
        private String content;

        public BookBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public BookBuilder setCover(String cover) {
            this.cover = cover;
            return this;
        }

        public BookBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
