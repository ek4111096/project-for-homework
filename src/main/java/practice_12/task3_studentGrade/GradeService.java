package practice_12.task3_studentGrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GradeService<T extends Number> {
    List<StudentGrade<T>> gradeList = new ArrayList<>();

    public synchronized void addGrade(StudentGrade<T> grade) {
        T gradeValue = grade.getGrade();
        if (gradeValue.doubleValue() > 0) {
            gradeList.add(grade);
        }
    }

    public Map<String, Double> getAverageGrade() {
        return gradeList.stream()
                .collect(Collectors.groupingBy(StudentGrade::getSubject,
                        Collectors.averagingDouble(
                                sg -> sg.getGrade().doubleValue()
                        )));
    }

    public List<StudentGrade<T>> getAll() {
        return List.copyOf(gradeList);
    }
}
