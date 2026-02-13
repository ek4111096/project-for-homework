package practice_2;

public class Main {
    public static void main(String[] args) {
        Car car = new Car(2000, "Mazda");

        car.setYear(2001);

        car.print();

        //Rectangle
        Rectangle rectangle = new Rectangle(5, 6);
        rectangle.setWidth(7);
        int area = rectangle.calculateArea(rectangle.width, rectangle.height);
        System.out.println("The area of rectangle "+ area);

        //Book
        Book book = new Book("Story", "Pushkin");
        book.setAuthor("Nabokov");
        book.printInfo();

        //BankAccount
        BankAccount  bankAccount = new BankAccount("Masha", 100);
        bankAccount.deposit(25);
        bankAccount.withdraw(10);
        bankAccount.printBalance();

        //Point
        Point point = new Point(4,5);
        point.setX(9);
        point.print();

        //StudentGroup
        StudentGroup studentGroup = new StudentGroup("Best students", 5);
        studentGroup.setStudentCount(9);
        studentGroup.printInfo();

        //Circle
        Circle circle = new Circle(22);
        circle.setRadius(15);
        System.out.println(circle.calculateArea(circle.radius));
        System.out.println(circle.calculateCircumference(circle.radius));

        //Techer
        Teacher teacher = new Teacher("Anna", "biology");
        teacher.setSubject("Math");
        teacher.printInfo();

        //Product
        Product product = new Product("Milk", 100);
        product.setPrice(90);
        product.applyDiscount(product.price);
        product.printInfo();

        //Laptop

        Laptop laptop = new Laptop("Lenovo", 1000);
        laptop.setPrice(1100);
        laptop.printInfo();

    }
}
