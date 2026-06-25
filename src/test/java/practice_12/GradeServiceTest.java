package practice_12;

import org.junit.jupiter.api.Test;
import practice_12.task3_studentGrade.GradeService;
import practice_12.task3_studentGrade.StudentGrade;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GradeServiceTest {
    /**
     * happy path
     * оценка добавилась в пустой список
     * оценка есть в списке
     * было добавлено то, что передано
     * список увеличился на 1 элемент
     * сравнение по полям
     * оценка добавлена в непустой список
     * добавление элементов разными потоками
     */

    GradeService<Double> service = new GradeService<>();
    StudentGrade<Double> expectedStudent = new StudentGrade<>("Sasha", "Bio", 5.0);
    int initialSizeOfService = service.getAll().size();
    StudentGrade<Double> expectedStudentNewObject = new StudentGrade<>("Sasha", "Bio", 5.0);
    StudentGrade<Double> expectedSecondStudent = new StudentGrade<>("Lena", "Math", 4.0);
    StudentGrade<Double> expectedSecondStudentewObject = new StudentGrade<>("Lena", "Math", 4.0);
    StudentGrade<Double> expectedThirdStudent = new StudentGrade<>("Gena", "Geo", 3.0);
    StudentGrade<Double> expectedThirdStudentewObject = new StudentGrade<>("Gena", "Geo", 3.0);
    StudentGrade<Double> expectedFourthStudent = new StudentGrade<>("Vena", "Geo", 2.0);
    StudentGrade<Double> expectedFourthStudentewObject = new StudentGrade<>("Vena", "Geo", 2.0);
    @Test
    public void addGradeTest() throws InterruptedException {
        service.addGrade(expectedStudent);
        StudentGrade<Double> actualStudent = service.getAll().getFirst();
        //было добавлено то, что передано
        //оценка добавилась в пустой список
        assertEquals(expectedStudentNewObject, actualStudent);
        //оценка есть в списке
        assertTrue(service.getAll().contains(expectedStudentNewObject));
        //список увеличился на 1 элемент
        assertEquals(1, service.getAll().size());
        //сравнение по полям
        assertEquals(expectedStudentNewObject.getGrade(), actualStudent.getGrade());
        assertEquals(expectedStudentNewObject.getStudentName(), actualStudent.getStudentName());
        assertEquals(expectedStudentNewObject.getSubject(), actualStudent.getSubject());

        //оценка добавлена в непустой список
        service.addGrade(expectedSecondStudent);
        StudentGrade<Double> actualSecondStudent = service.getAll().getLast();
        assertEquals(expectedSecondStudentewObject, actualSecondStudent);
        assertTrue(service.getAll().contains(actualSecondStudent));
        assertEquals(2, service.getAll().size());

        //добавление элементов разными потоками
        Thread thread1 = new Thread(() -> service.addGrade(expectedThirdStudent));
        Thread thread2 = new Thread(() -> service.addGrade(expectedFourthStudent));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        StudentGrade<Double> actualThirdStudent = service.getAll().get(2);
        StudentGrade<Double> actualFourthStudent = service.getAll().getLast();

        assertEquals(expectedThirdStudentewObject, actualThirdStudent);
        assertEquals(expectedFourthStudentewObject, actualFourthStudent);
        assertEquals(4, service.getAll().size());

    }

    //проверка что средний балл считается верно по всем предметам
    @Test
    public void getAverageGradeTest() {
        service.addGrade(expectedStudent);
        service.addGrade(expectedSecondStudent);
        service.addGrade(expectedThirdStudent);
        service.addGrade(expectedFourthStudent);

        Map<String, Double> result = service.getAverageGrade();
        assertEquals(4.0, result.get("Math"));
        assertEquals(2.5, result.get("Geo"));
        assertEquals(5.0, result.get("Bio"));

    }

}
