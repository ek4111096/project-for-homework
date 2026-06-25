package practice_12;

import org.junit.jupiter.api.Test;
import practice_12.task1_universalEntityManager.Client;
import practice_12.task1_universalEntityManager.Entity;
import practice_12.task1_universalEntityManager.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * добавить 1 элемент в пустой менеджер
 * добавить 1 элемент в НЕпустой менеджер
 * добавить элементы несколькими потоками
 */

public class EntityManagerTest {
    @Test
    public void addEntityTest() throws InterruptedException {
        EntityManager<Entity> manager = new EntityManager<>();
        int initialSize = manager.getAll().size();
        Entity expectedClient = new Client(20, "Vadim", true);
        manager.add(expectedClient);

        //добавление элемента
        //ожидание 1: элемент содержится в финальном списке
        //ожидание 2: было добавлено то, что передано
        //ожидание 3: добавлен 1 элемент

        //ожидание 1
        assertTrue(manager.getAll().contains(expectedClient));
        Entity actualClient = manager.getAll().getFirst();
        Entity expectedClientNewObject = new Client(20, "Vadim", true);
        //ожидание 2
        assertEquals(expectedClientNewObject, actualClient);
        //по полям
        assertEquals(expectedClientNewObject.getAge(), actualClient.getAge());
        assertEquals(expectedClientNewObject.getName(), actualClient.getName());
        assertEquals(expectedClientNewObject.isActive(), actualClient.isActive());
        //ожидание 3
        assertEquals(initialSize + 1, manager.getAll().size());

        //добавление элемента в НЕпустой менеджер
        //ожидание 1: элемент содержится в финальном списке
        //ожидание 2: было добавлено то, что передано
        //ожидание 3: добавлен 1 элемент

        //ожидание 1
        Entity secondExpectedClient = new Client(25, "Katya", true);
        manager.add(secondExpectedClient);
        assertTrue(manager.getAll().contains(secondExpectedClient));

        //ожидание 2
        Entity secondActualClient = manager.getAll().getLast();
        Entity secondExpectedClientNewObject = new Client(25, "Katya", true);
        assertEquals(secondExpectedClientNewObject, secondActualClient);

        assertEquals(secondExpectedClientNewObject.getAge(), secondActualClient.getAge());
        assertEquals(secondExpectedClientNewObject.getName(), secondActualClient.getName());
        assertEquals(secondExpectedClientNewObject.isActive(), secondActualClient.isActive());

        //ожидание 3
        assertEquals(initialSize + 2, manager.getAll().size());

        //1 добавление 1 элемента несколькими потоками
        //ожидание 1: элемент содержится в финальном списке
        //ожидание 2: каждый поток добавил по 1 элементу
        //ожидание 3: добавилось то, что передавал каждый поток

        Entity thirdExpectedClient = new Client(15, "Lena", true);
        Entity fourthExpectedClient = new Client(35, "Dasha", true);
        Thread thread1 = new Thread(() -> manager.add(thirdExpectedClient));
        Thread thread2 = new Thread(() -> manager.add(fourthExpectedClient));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertTrue(manager.getAll().contains(thirdExpectedClient));
        assertTrue(manager.getAll().contains(fourthExpectedClient));

        //ожидание 2
        assertEquals(initialSize + 4, manager.getAll().size());

        //ожидание 3
        Entity thirdExpectedClientNewObject = new Client(15, "Lena", true);
        Entity fourthExpectedClientNewObject = new Client(35, "Dasha", true);
        assertEquals(thirdExpectedClientNewObject, manager.getAll().get(2));
        assertEquals(fourthExpectedClientNewObject, manager.getAll().getLast());
    }

    /**
     * удалить 1 элемент из пустого менеджера
     * удалить 1 элемент из НЕпустого менеджера
     * удалить 1 элемент несколькими потоками
     */

    @Test
    public void removeElementTest() throws InterruptedException {
        EntityManager<Entity> manager = new EntityManager<>();
        Entity firstExpectedClient = new Client(55, "Pasha", true);
        Entity secondExpectedClient = new Client(45, "Masha", true);
        Entity thirdExpectedClient = new Client(35, "Sasha", true);
        manager.add(firstExpectedClient);
        manager.add(secondExpectedClient);

        int currentEntitiesSize = manager.getAll().size();

        //удаление 1 элемент из НЕпустого менеджера
        //ожидание 1: удалился нужный элемент
        //ожидание 2: размер менеджера уменьшился на 1 элемент
        //ожидание 3: размер менеджера уменьшился ещё на 1 элемент

        manager.removeElement(secondExpectedClient);
        //ожидание 1
        assertFalse(manager.getAll().contains(secondExpectedClient));
        //ожидание 2
        assertEquals(currentEntitiesSize - 1, manager.getAll().size());
        //ожидание 3
        manager.removeElement(firstExpectedClient);
        assertEquals(0, manager.getAll().size());

        //удаление 1 элемент из пустого менеджера
        //ожидание 1: неудачная попытка удаления 1 элемента

        assertFalse(manager.removeElement(firstExpectedClient));

        //удаление 1 элемент несколькими потоками
        //ожидание 1: удаление одного элемента двумя потоками
        manager.add(firstExpectedClient);
        manager.add(secondExpectedClient);
        manager.add(thirdExpectedClient);


        Thread thread1 = new Thread(() -> manager.removeElement(firstExpectedClient));
        Thread thread2 = new Thread(() -> manager.removeElement(firstExpectedClient));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertFalse(manager.getAll().contains(firstExpectedClient));
        //ожидание 2: удаление разных элементов двумя потоками
        Thread thread3 = new Thread(() -> manager.removeElement(secondExpectedClient));
        Thread thread4 = new Thread(() -> manager.removeElement(thirdExpectedClient));

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
        Entity fourthExpectedClient = new Client(17, "Pasha", true);
        Entity fifthExpectedClient = new Client(61, "Masha", true);
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
     * ожидание 2: в списке не содержатся объекты, с именами отличными от имени Sasha
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
    public void filteredByStatusTest() {
        EntityManager<Entity> manager = new EntityManager<>();
        Entity firstExpectedClient = new Client(18, "Pasha", false);
        Entity secondExpectedClient = new Client(38, "Sasha", true);
        Entity thirdExpectedClient = new Client(60, "Sasha", true);
        manager.add(firstExpectedClient);
        manager.add(secondExpectedClient);
        manager.add(thirdExpectedClient);
        List<Entity> filtered = manager.filteredByStatus(true);
        //ожидание 1
        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(secondExpectedClient));
        assertTrue(filtered.contains(thirdExpectedClient));
        //ожидание 2
        assertFalse(filtered.contains(firstExpectedClient));
    }

}
