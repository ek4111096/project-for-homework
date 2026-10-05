package programmingorinciples;

abstract class Bird {
}

class FlyingBird extends Bird{
    public void fly() {
        System.out.println("Птица летит");
    }
}
class Penguin extends Bird {
    public void swim() {
        System.out.println("Пингвин плавает");
    }
}
