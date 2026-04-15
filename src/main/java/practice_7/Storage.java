package practice_7;

public class Storage <T> {
    private T element;

    public Storage(T element) {
        this.element = element;
    }

    public T getElement() {
        return this.element;
    }
}
