public class MathOperations {
    public static void main(String[] args) {
        int sum = add(5,3);
        System.out.println("The result of method add " + sum);

        int sum1 = subtract(5,3);
        System.out.println("The result of method subtract " + sum1);

        int sum2 = multiply(6,8);
        System.out.println("The result of method multiply " + sum2);

        double sum3 = divide(4,3);
        System.out.println("The result of method divide " + sum3);

        int max = findMax(5,3);
        System.out.println("The result of method findMax " + max);

        int module = difference(7,8);
        System.out.println("The result of method difference " + module);

        int sqArea = squareArea(8);
        System.out.println("The result of method squareArea " + sqArea);

        int perimeter = squarePerimeter(6);
        System.out.println("The result of method squarePerimeter " + perimeter);

        double minutes = convertSecondsToMinutes(800);
        System.out.println("The result of method convertSecondsToMinutes " + minutes);

        double speed1 = averageSpeed(100, 3);
        double speed2 = averageSpeed(300, 7);
        System.out.println("The results of method averageSpeed " + speed1 + ", " + speed2);

        double hypotenuse = findHypotenuse(7, 8);
        double hypotenuse1 = findHypotenuse(9,2);
        System.out.println("The results of method findHypotenuse " + hypotenuse + ", " + hypotenuse1);

        double circle1 = circleCircumference(5);
        double circle2 = circleCircumference(6);
        System.out.println("The results of method circleCircumference " + circle1 + ", " + circle2);

        double percent = calculatePercentage(922.0, 4.0);
        System.out.println("The result of method calculatePercentage " + percent);

        double celsius = celsiusToFahrenheit(8);
        System.out.println("The result of method celsiusToFahrenheit " + celsius);

        double fahrenheit = fahrenheitToCelsius(6);
        System.out.println("The result of method fahrenheitToCelsius " + fahrenheit);

    }
    public static int add(int x, int y) {
        return x + y;
    }
    public static int subtract(int x, int y) {
        return x - y;
    }

    public static int multiply(int x, int y){
        return x * y;
    }

    public static double divide(int x, int y) {
        return x / y;
    }

    public static int findMax(int a, int b) {
        return Math.max(a,b);
    }
    public static int difference(int x, int y) {
        return Math.abs(x - y);
    }

    public static int squareArea(int side) {
        return side * side;
    }

    public static int squarePerimeter(int side) {
        return  4 * side;
    }

    public static double convertSecondsToMinutes(int seconds) {
        return seconds / 60.0;
    }

    public static double averageSpeed(double distance, double time) {
        return distance / time;
    }

    public static double  findHypotenuse(double a, double b) {
        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("Parameteres must be positive");
        }
        return Math.sqrt(a*a + b*b);
    }

    public static double  circleCircumference(double radius) {
        return 2 * Math.PI * radius;
    }

    public static double calculatePercentage(double total, double part) {
        if (total <= 0) {
            throw new IllegalArgumentException("total must be bigger than 0");
        }
        return (part / total) * 100;
    }

    public static double celsiusToFahrenheit(double c) {
        return c * 9 / 5 + 32;
    }

    public static double fahrenheitToCelsius(double f) {
        return (f - 32) * 5 / 9;
    }
}
