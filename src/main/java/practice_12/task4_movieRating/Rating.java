package practice_12.task4_movieRating;

public class Rating<T extends Number> {
    private T value;

    public Rating(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
