package practice_3;


public class LibraryTest {
  

    public static void main(String[] args) {
        Library library = new Library();
        //library.bookTitle; field "bookTitle" is not available
        String title = library.getBookTitle(); //i can get the value of field bookTitle via getter
        String author = library.author; //is available
        String category = library.category; //is available
        int year = library.year; //is available

    }


}
