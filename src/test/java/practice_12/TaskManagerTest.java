package practice_12;

import org.junit.jupiter.api.Test;
import practice_12.task6_taskManager.Task;
import practice_12.task6_taskManager.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    TaskService<UUID> service = new TaskService<>();

    /**
     * добавление задачи
     * в пустой список
     * в непустой список
     * размер списка
     * содержится в списке
     * добавилось то, что передали
     * добавление 2мя потоками
     */


    @Test
    public void addTaskTest() throws InterruptedException {
        Task<UUID> task = new Task<>(LocalDate.of(2026, 6, 23), 1, "Active", UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task1 = new Task<>(LocalDate.of(2026, 6, 22), 1, "Active", UUID.fromString("333e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task3 = new Task<>(LocalDate.of(2026, 6, 22), 1, "Active", UUID.fromString("433e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task4 = new Task<>(LocalDate.of(2026, 6, 22), 1, "Active", UUID.fromString("533e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> taskNewObject = new Task<>(LocalDate.of(2026, 6, 23), 1, "Active", UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        service.addTask(task);
        assertEquals(1, service.tasks.size());
        assertTrue(service.tasks.contains(task));
        assertEquals(taskNewObject, service.tasks.getFirst());
        service.addTask(task1);
        assertEquals(2, service.tasks.size());
        assertTrue(service.tasks.contains(task1));
        assertThrows(RuntimeException.class, () -> {
            service.addTask(task1);
        });
        Thread thread1 = new Thread(() -> service.addTask(task3));
        Thread thread2 = new Thread(() -> service.addTask(task4));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(4, service.tasks.size());
        assertTrue(service.tasks.contains(task3));
        assertTrue(service.tasks.contains(task4));
    }

    /**
     * удаление из пустого списка
     * удаление из непустого списка
     * размер спика
     * задача не содержится в списке
     */

    @Test
    public void removeTaskTest() throws InterruptedException {
        Task<UUID> task = new Task<>(LocalDate.of(2026, 6, 23), 1, "Active", UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task1 = new Task<>(LocalDate.of(2026, 6, 22), 1, "Active", UUID.fromString("333e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task3 = new Task<>(LocalDate.of(2026, 6, 22), 1, "Active", UUID.fromString("433e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task4 = new Task<>(LocalDate.of(2026, 6, 22), 1, "Active", UUID.fromString("533e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> taskNewObject = new Task<>(LocalDate.of(2026, 6, 23), 1, "Active", UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        service.removeTask(task.getID());
        assertEquals(0, service.tasks.size());
        service.addTask(task);
        service.addTask(task1);
        service.addTask(task3);
        service.removeTask(task.getID());
        assertEquals(2, service.tasks.size());
        assertFalse(service.tasks.contains(task));
        Thread thread1 = new Thread(() -> service.removeTask(task1.getID()));
        Thread thread2 = new Thread(() -> service.removeTask(task3.getID()));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(0, service.tasks.size());
        assertFalse(service.tasks.contains(task3));
        assertFalse(service.tasks.contains(task1));


    }

    @Test
    public void findTaskByStatusTest() {
        Task<UUID> task = new Task<>(LocalDate.of(2026, 6, 23), 1, "Active", UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task1 = new Task<>(LocalDate.of(2026, 6, 22), 1, "Active", UUID.fromString("333e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task3 = new Task<>(LocalDate.of(2026, 6, 22), 1, "Active", UUID.fromString("433e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task2 = new Task<>(LocalDate.of(2026, 6, 22), 1, "Closed", UUID.fromString("533e4567-e89b-12d3-a456-426614174000"));
        service.addTask(task);
        service.addTask(task1);
        service.addTask(task2);
        service.addTask(task3);

        List<Task<UUID>> actualResult = service.findTaskByStatus("Active");
        assertEquals(3, actualResult.size());
        assertFalse(actualResult.contains(task2));
    }

    @Test
    public void findTaskByPriorityTest() {
        Task<UUID> task = new Task<>(LocalDate.of(2026, 6, 23), 1, "Active", UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task1 = new Task<>(LocalDate.of(2026, 6, 22), 2, "Active", UUID.fromString("333e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task3 = new Task<>(LocalDate.of(2026, 6, 22), 3, "Active", UUID.fromString("433e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task2 = new Task<>(LocalDate.of(2026, 6, 22), 4, "Closed", UUID.fromString("533e4567-e89b-12d3-a456-426614174000"));
        service.addTask(task);
        service.addTask(task1);
        service.addTask(task2);
        service.addTask(task3);

        List<Task<UUID>> actualResult = service.findTaskByPriority(3);
        assertEquals(2, actualResult.size());
        assertTrue(actualResult.contains(task));
        assertFalse(actualResult.contains(task3));
    }

    @Test
    public void sortedTasksTest() {
        Task<UUID> task = new Task<>(LocalDate.of(2026, 6, 23), 1, "Active", UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task1 = new Task<>(LocalDate.of(2026, 6, 22), 2, "Active", UUID.fromString("333e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task3 = new Task<>(LocalDate.of(2026, 6, 21), 3, "Active", UUID.fromString("433e4567-e89b-12d3-a456-426614174000"));
        Task<UUID> task2 = new Task<>(LocalDate.of(2026, 6, 20), 4, "Closed", UUID.fromString("533e4567-e89b-12d3-a456-426614174000"));
        List<Task<UUID>> result = service.sortedTasks();
        assertTrue(result.isEmpty());
        service.addTask(task);
        service.addTask(task1);
        service.addTask(task2);
        service.addTask(task3);
        List<Task<UUID>> actualResult = service.sortedTasks();
        assertEquals(task2, actualResult.get(0));
        assertEquals(task3, actualResult.get(1));
        assertEquals(task1, actualResult.get(2));
        assertEquals(task, actualResult.get(3));


    }



}
