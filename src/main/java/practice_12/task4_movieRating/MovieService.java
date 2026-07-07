package practice_12.task4_movieRating;

import java.util.*;
import java.util.stream.Collectors;

public class MovieService<T extends Number> {
    public Map<Movie, List<Rating<T>>> moviesRating = new HashMap<>();

    public synchronized void addRate(Movie movie, T rate) {
        if (rate.doubleValue() > 0 && rate.doubleValue() < 11) {
            List<Rating<T>> ratings = moviesRating.get(movie);
            if (ratings == null) {
                ratings = new ArrayList<>();
                moviesRating.put(movie, ratings);
            }
            ratings.add(new Rating<>(rate));
        } else {
            throw new RuntimeException("Enter valid rate from 1 to 10");
        }
    }

    public Map<Movie, Double> getAvgMovieRate() {
        return moviesRating.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .mapToDouble(rating -> rating.getValue().doubleValue())
                                .average()
                                . orElse(0.0)
                ));
    }

    public Map<Movie, Double> getSortedMoviesByRating() {
        Map<Movie, Double> avgMovieRating = moviesRating.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            double avg = entry.getValue()
                                    .stream()
                                    .mapToDouble(rating -> rating.getValue().doubleValue())
                                    .average()
                                    .orElse(0.0);
                            return  Math.round(avg * 10) / 10.0;
                        }
                ));
        Map<Movie, Double> sortedResult = avgMovieRating.entrySet()
                .stream()
                .sorted(Map.Entry.<Movie, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        () -> new LinkedHashMap<>()
                ));
        return sortedResult;
    }

}
