package practice_12.task6_taskManager;

import jdk.jfr.DataAmount;

import java.time.LocalDate;
import java.util.Objects;

public class Task<T> {
    private T ID;
    private String status;
    private int priority;
    private LocalDate date;

    public Task(LocalDate date, int priority, String status, T ID) {
        this.date = date;
        this.priority = priority;
        this.status = status;
        this.ID = ID;
    }

    public T getID() {
        return ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int pririty) {
        this.priority = pririty;
    }

    public void setID(T ID) {
        this.ID = ID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task<?> task = (Task<?>) o;
        return priority == task.priority && Objects.equals(ID, task.ID) && Objects.equals(status, task.status) && Objects.equals(date, task.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, status, priority, date);
    }
}
