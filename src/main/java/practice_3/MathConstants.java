package practice_3;

public class MathConstants {
    static final double PI = 3.14159;
    static final double E = 2.71828; //i don't know where i need to use this constant

    static double calculateCircleArea(double r){
        return MathConstants.PI * r * r; //changed to my constant
    }

    static double calculateCircumference(double r) {
        return 2 * PI * r;
    }
}
