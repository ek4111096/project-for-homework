package practice_2;

public class Car {
    int year;
    String brand;

    Car(int year, String brand){
        this.year = year;
        this.brand = brand;
    }

    int getYear(){
        return this.year;
    }

    String getBrand(){
        return this.brand;
    }

    void setYear(int newYear){
        this.year = newYear;
    }

    void setBrand(String newBrand){
        this.brand = newBrand;
    }

    void print(){
        System.out.println("The brand is " + brand + ", the year is " + year);
    }

}
