package practice_12;

import org.junit.jupiter.api.Test;
import practice_12.task4_movieRating.Movie;
import practice_12.task4_movieRating.MovieService;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MovieServiceTest {
    /**
     * размер списка оценок при добавление в пустой/НЕпустой рэйтинг
     * добавили то, что передавали
     * добавили разными потоками
     */

    @Test
    public void addRateTest() throws InterruptedException {
        MovieService<Double> service = new MovieService<>();
        Movie<Double> titanic = new Movie<>("Titanik");
        service.addRate(titanic, 8.0);
        assertEquals(1, service.moviesRating.get(titanic).size());
        assertEquals(8.0, service.moviesRating.get(titanic).get(0).getValue());
        service.addRate(titanic, 9.0);
        assertEquals(2, service.moviesRating.get(titanic).size());
        assertEquals(9.0, service.moviesRating.get(titanic).get(1).getValue());
        Thread thread1 = new Thread(() -> service.addRate(titanic, 7.0));
        Thread thread2 = new Thread(() -> service.addRate(titanic, 10.0));

        thread1.start();
        Thread.sleep(500);
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(4, service.moviesRating.get(titanic).size());
        assertEquals(7.0, service.moviesRating.get(titanic).get(2).getValue());
        assertEquals(10.0, service.moviesRating.get(titanic).get(3).getValue());
    }

    /**
     * 1 rate
     * нет рейтинга
     */

    @Test
    public void getAvgMovieRateTest(){
        MovieService<Integer> service = new MovieService<>();
        Movie<Integer> titanic = new Movie<>("Titanik");
        assertNull(service.getAvgMovieRate().get(titanic));
        service.addRate(titanic, 7);
        assertEquals(7, service.getAvgMovieRate().get(titanic));
        service.addRate(titanic, 8);
        service.addRate(titanic, 9);
        Map<Movie, Double> resultAvgRate = service.getAvgMovieRate();
        assertEquals(8, resultAvgRate.get(titanic));
        assertThrows(RuntimeException.class, () -> {
            service.addRate(titanic, 12);
        });

    }

    @Test
    public void getSortedMoviesByRatingTest() {
        MovieService<Integer> service = new MovieService<>();
        Movie<Integer> titanic = new Movie<>("Titanik");
        service.addRate(titanic, 7);
        service.addRate(titanic, 8);
        service.addRate(titanic, 9);

        Movie<Integer> avatar = new Movie<>("Avatar");
        service.addRate(avatar, 4);
        service.addRate(avatar, 3);
        service.addRate(avatar, 2);

        Movie<Integer> interstellar = new Movie<>("Interstellar");
        service.addRate(interstellar, 10);
        service.addRate(interstellar, 10);
        service.addRate(interstellar, 9);

        Map<Movie, Double> actualResult = service.getSortedMoviesByRating();
        Map<Movie, Double> expectedResult = new LinkedHashMap<>();
        expectedResult.put(new Movie<>("Interstellar"), 9.7);
        expectedResult.put(new Movie<>("Titanik"), 8.0);
        expectedResult.put(new Movie<>("Avatar"), 3.0);

        assertEquals(expectedResult, actualResult);

    }

}
