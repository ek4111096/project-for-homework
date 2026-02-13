package practice_2;

public class Book {
    String title;
    String author;

    Book(String title, String author){
        this.title = title;
        this.author = author;
    }

    String getTitle(){
        return this.title;
    }

    String getAuthor(){
        return this.author;
    }

    void setTitle(String newTitle){
        this.title = newTitle;
    }

    void setAuthor(String newAuthor){
        this.author = newAuthor;
    }

    void printInfo(){
        System.out.println("The title of the book is " + this.title + ", the author is " + this.author);
    }
}
