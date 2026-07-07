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

    public synchronized void removeTask(T taskId) {
        Iterator<Task<T>> iterator = tasks.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getID().equals(taskId)) {
                iterator.remove();
                return;
            }
        }
    }

    public List<Task<T>> findTaskByStatus(String status) {
        return tasks.stream()
                .filter(t -> Objects.equals(t.getStatus(), status))
                .collect(Collectors.toList());
    }

    public List<Task<T>> findTaskByPriority(int maxPriority) {
        return tasks.stream()
                .filter(t -> t.getPriority() < maxPriority)
                .collect(Collectors.toList());
    }

    public List<Task<T>> sortedTasks() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDate))
                .collect(Collectors.toList());
    }

}
