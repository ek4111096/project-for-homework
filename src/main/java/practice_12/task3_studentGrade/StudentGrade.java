package practice_12.task3_studentGrade;

import java.util.Objects;

public class StudentGrade<T extends Number> {
    private String studentName;
    private String subject;
    private T grade;

    public StudentGrade(String studentName, String subject, T grade) {
        this.studentName = studentName;
        this.subject = subject;
        setGrade(grade);
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public T getGrade() {
        return grade;
    }

    public void setGrade(T grade) {
        if (grade.doubleValue() < 0) {
            throw new InvalidGradeException("Grade shoud be > 0");
        }
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StudentGrade<?> that = (StudentGrade<?>) o;
        return Objects.equals(studentName, that.studentName) && Objects.equals(subject, that.subject) && Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, subject, grade);
    }
}
