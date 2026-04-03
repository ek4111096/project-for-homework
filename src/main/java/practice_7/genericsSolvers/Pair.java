package practice_7.genericsSolvers;

public class Pair <T, U> {
    private T elementT;
    private U elementU;

    public T getElementT() {
        return this.elementT;
    }

    public U getElementU() {
        return this.elementU;
    }

    public void setElementT(T elementT) {
        this.elementT = elementT;
    }

    public void setElementU(U elementU) {
        this.elementU = elementU;
    }
}
