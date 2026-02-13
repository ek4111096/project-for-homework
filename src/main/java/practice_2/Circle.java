package practice_2;

public class Circle {
    double radius;

    Circle(double radius){
        this.radius = radius;
    }

    double getRadius(){
        return this.radius;
    }

    void setRadius(double newRadius){
        this.radius = newRadius;
    }

    double calculateArea(double radius){
        return Math.PI * radius * radius;
    }

    double calculateCircumference(double radius){
        return 2 * Math.PI * radius;
    }
}
