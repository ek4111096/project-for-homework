package practice_12.task1_universalEntityManagerTest;

import org.junit.jupiter.api.Test;
import practice_12.task1_universalEntityManager.Client;
import practice_12.task1_universalEntityManager.Entity;
import practice_12.task1_universalEntityManager.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест дизайн:
 * добавить 1 элемент в пустой менеджер
 * добавить 1 элемент в НЕпустой менеджер
 * добавить 1 элемент несколькими потоками (проверка интеграции)
 */

public class EntityManagerTest {
    @Test
    public void addEntityTest() throws InterruptedException {
        EntityManager<Entity> manager = new EntityManager<>();

        int initialSizeOfEntities = manager.getAll().size();

        Entity expectedClient = new Client(20, "Vlad", true);

        manager.add(expectedClient);

        //добавление элемента
        //ожидание 1: элемент содержится в финальном списке
        //ожидание 2: было добавлено то, что передано
        //ожидание 3: добавлен 1 элемент

        //ожидание 1
        assertTrue(manager.getAll().contains(expectedClient));

        Entity actualClient = manager.getAll().getFirst();

        Entity expectedClientNewObject = new Client(20, "Vlad", true);

        //ожидание 2: сравнение как объект
        assertEquals(expectedClientNewObject, actualClient);

        //ожидание 2: сравнение по полям
        assertEquals(expectedClientNewObject.getAge(), actualClient.getAge());
        assertEquals(expectedClientNewObject.getName(), actualClient.getName());
        assertEquals(expectedClientNewObject.IsActive(), actualClient.IsActive());

        //ожидание 3
        assertEquals(initialSizeOfEntities + 1, manager.getAll().size());

        //добавление элемента в НЕпустой менеджер
        //ожидание 1: элемент содержится в финальном списке
        //ожидание 2: было добавлено то, что передано
        //ожидание 3: добавлен 1 элемент

        //ожидание 1
        Entity secondExpectedClient = new Client(25, "Katya", true);
        manager.add(secondExpectedClient);
        assertTrue(manager.getAll().contains(secondExpectedClient));

        //ожидание 2
        Entity secondActualResult = manager.getAll().getLast();
        Entity secondExpectedClientNewObject = new Client(25, "Katya", true);
        assertEquals(secondExpectedClientNewObject, secondActualResult);

        //ожидание 2: сравнение по полям
        assertEquals(secondExpectedClientNewObject.getName(), secondActualResult.getName());
        assertEquals(secondExpectedClientNewObject.getAge(), secondActualResult.getAge());
        assertEquals(secondExpectedClientNewObject.IsActive(), secondActualResult.IsActive());

        //ожидание 3
        assertEquals(initialSizeOfEntities + 2, manager.getAll().size());

        //добавление 1 элемент несколькими потоками (проверка интеграции)
        //ожидание 1: добавленный элемент содержится в финальном списке
        //ожидание 2: каждый поток добавил по 1 элементу
        //ожидание 3: добавилось именно то, что добавлял каждый поток

        Entity thirdExpectedClient = new Client(15, "Lena", true);
        Entity fourthExpectedClient = new Client(35, "Dasha", true);
        Thread thread1 = new Thread(() -> manager.add(thirdExpectedClient));
        Thread thread2 = new Thread(() -> manager.add(fourthExpectedClient));
        //ожидание 1
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertTrue(manager.getAll().contains(thirdExpectedClient));
        assertTrue(manager.getAll().contains(fourthExpectedClient));

        //ожидание 2
        assertEquals(initialSizeOfEntities + 4, manager.getAll().size());

        //ожидание 3
        Entity thirdExpectedClientNewObject = new Client(15, "Lena", true);
        Entity fourthExpectedClientNewObject = new Client(35, "Dasha", true);
        assertEquals(thirdExpectedClientNewObject, manager.getAll().get(2));
        assertEquals(fourthExpectedClientNewObject, manager.getAll().getLast());




    }
    /**
     * Тест дизайн:
     * удалить 1 элемент из НЕпустого менеджера
     * удалить 1 элемент из пустого менеджера
     * удалить 1 элемент несколькими потоками
     */
    @Test
    public void removeElementTest() throws InterruptedException {
        EntityManager<Entity> manager = new EntityManager<>();

        int initialSizeOfEntities = manager.getAll().size();

        Entity firstExpectedClient = new Client(55, "Pasha", true);
        Entity secondExpectedClient = new Client(45, "Masha", true);
        Entity thirdExpectedClient = new Client(35, "Sasha", true);
        manager.add(firstExpectedClient);
        manager.add(secondExpectedClient);

        int currentSizeOfEntities = manager.getAll().size();

        //удаление одного элемента из НЕпустого менеджера
        //ожидание 1: удалился нужный элемент
        //ожидание 2: размер уменьшился на 1 элемент -> 1
        //ожидание 3: размер снова уменьшился на 1 элемент -> 0

        manager.removeElement(secondExpectedClient);

        //ожидание 1
        assertFalse(manager.getAll().contains(secondExpectedClient));
        //ожидание 2
        assertEquals(currentSizeOfEntities - 1, manager.getAll().size());
        //ожидание 3
        manager.removeElement(firstExpectedClient);
        assertEquals(0, manager.getAll().size());

        //удаление одного элемента из пустого менеджера
        //ожидание 1: неудачная попытка удаления одного элемента
        assertFalse(manager.removeElement(firstExpectedClient));


        //удаление одного элемента несколькими потоками
        //ожидание 1: два потока одновременно пытаются удалить один и тот же элемент -> элемент удален
        manager.add(firstExpectedClient);
        manager.add(secondExpectedClient);
        manager.add(thirdExpectedClient);
        Thread thread1 = new Thread(() -> manager.removeElement(firstExpectedClient));
        Thread thread2 = new Thread(() -> manager.removeElement(firstExpectedClient));
        //ожидание 1
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        assertFalse(manager.getAll().contains(firstExpectedClient));
        //ожидание 2: два потока одновременно пытаются удалить разные элементы -> элементы удалены
        Thread thread3 = new Thread(() -> manager.removeElement(secondExpectedClient));
        Thread thread4 = new Thread(() -> manager.removeElement(thirdExpectedClient));
        //ожидание 1
        thread3.start();
        thread4.start();

        thread3.join();
        thread4.join();
        assertEquals(0, manager.getAll().size());
        assertFalse(manager.getAll().contains(secondExpectedClient));
        assertFalse(manager.getAll().contains(thirdExpectedClient));
    }

    /**
     * Тест дизайн
     * условие: возрастной диапазон 18-60 включительно
     * ожидание 1: в списке содержатся объекты с возрастом в диапазоне от 18 до 60 лет включительно
     * ожидание 2: в списке не содержатся объекты, с возрастом до 18 и более 60 лет
     */

    @Test
    public void filteredByAgeTest() {
        EntityManager<Entity> manager = new EntityManager<>();
        Entity firstExpectedClient = new Client(18, "Pasha", true);
        Entity secondExpectedClient = new Client(38, "Masha", true);
        Entity thirdExpectedClient = new Client(60, "Sasha", true);
        Entity fourthExpectedClient = new Client(17, "Glasha", true);
        Entity fifthExpectedClient = new Client(61, "Dasha", true);
        manager.add(firstExpectedClient);
        manager.add(secondExpectedClient);
        manager.add(thirdExpectedClient);
        manager.add(fourthExpectedClient);
        manager.add(fifthExpectedClient);
        List<Entity> filtered = manager.filteredByAge(18, 60);
        //ожидание 1
        assertTrue(filtered.contains(firstExpectedClient));
        assertTrue(filtered.contains(secondExpectedClient));
        assertTrue(filtered.contains(thirdExpectedClient));

        //ожидание 2
        assertFalse(filtered.contains(fourthExpectedClient));
        assertFalse(filtered.contains(fifthExpectedClient));

    }

    /**
     * Тест дизайн
     * условие: имя Sasha
     * ожидание 1: в списке содержатся только объекты с именем Sasha
     * ожидание 2: в списке не содержатся объекты с именами, отличными от Sasha
     */

    @Test
    public void filteredByNameTest() {
        EntityManager<Entity> manager = new EntityManager<>();
        Entity firstExpectedClient = new Client(18, "Pasha", true);
        Entity secondExpectedClient = new Client(38, "Sasha", true);
        Entity thirdExpectedClient = new Client(60, "Sasha", true);
        manager.add(firstExpectedClient);
        manager.add(secondExpectedClient);
        manager.add(thirdExpectedClient);
        List<Entity> filtered = manager.filteredByName("Sasha");
        //ожидание 1
        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(secondExpectedClient));
        assertTrue(filtered.contains(thirdExpectedClient));
        //ожидание 2
        assertFalse(filtered.contains(firstExpectedClient));
    }

    /**
     * Тест дизайн
     * ожидание 1: в списке содержатся только объекты с полем isActive
     * ожидание 2: в списке содержатся только объекты с полем isActive - false
     */

    @Test
    public void filteredByISActiveTest() {
        EntityManager<Entity> manager = new EntityManager<>();
        Entity firstExpectedClient = new Client(18, "Pasha", false);
        Entity secondExpectedClient = new Client(38, "Sasha", true);
        Entity thirdExpectedClient = new Client(60, "Sasha", true);
        manager.add(firstExpectedClient);
        manager.add(secondExpectedClient);
        manager.add(thirdExpectedClient);
        List<Entity> filtered = manager.filteredByISActive(true);
        //ожидание 1
        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(secondExpectedClient));
        assertTrue(filtered.contains(thirdExpectedClient));
        //ожидание 2
        assertFalse(filtered.contains(firstExpectedClient));
    }

}
