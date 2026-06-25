package practice_12.task6_taskManager;

import java.util.*;
import java.util.stream.Collectors;

public class TaskService<T> {
    private static final String ACTIVE = "Active";
    public List<Task<T>> tasks = new ArrayList<>();

    public synchronized void addTask(Task<T> task) {
        if (tasks.contains(task)) {
            throw new RuntimeException("Task already exist");
        }
        tasks.add(task);
    }

    public synchronized void removeTask(Task<T> task) {
        T taskId = task.getID();

        Iterator<Task<T>> iterator = tasks.iterator();

        while (iterator.hasNext()) {
            Task<T> t = iterator.next();

            if (t.getID().equals(taskId)) {
                iterator.remove();
                return;
            }
        }
    }

    public List<Task<T>> findTaskByStatus() {
        return tasks.stream()
                .filter(t -> Objects.equals(t.getStatus(), ACTIVE))
                .collect(Collectors.toList());
    }

    public List<Task<T>> findTaskByPriority() {
        return tasks.stream()
                .filter(t -> t.getPriority() < 3)
                .collect(Collectors.toList());
    }

    public List<Task<T>> sortedTasks() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDate))
                .collect(Collectors.toList());
    }

}
