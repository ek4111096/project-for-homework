package practice_12.task4_movieRating;

import java.util.Objects;

public class Movie<T extends Number> {
    private final String name;
    private Rating<T> rate;

    public Movie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Rating<T> getRate() {
        return rate;
    }

    public void setRate(Rating<T> rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie<?> movie = (Movie<?>) o;
        return Objects.equals(name, movie.name) && Objects.equals(rate, movie.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rate);
    }
}
