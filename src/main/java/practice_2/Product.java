package practice_2;

public class Product {
    String name;
    double price;

    Product(String name, double price){
        this.name = name;
        this.price = price;
    }

    String getName(){
        return this.name;
    }
    double getPrice(){
        return this.price;
    }

    void setPrice(double newPrice){
        this.price = newPrice;
    }

    double applyDiscount(double discount) {
        return this.price = this.price * 0.05;
    }

    void printInfo(){
        System.out.println("Name of product is " + this.name + ", price is " + this.price);
    }
}
