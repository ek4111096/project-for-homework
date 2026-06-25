package practice_12.task1_universalEntityManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class EntityManager <T extends Entity> {
    CopyOnWriteArrayList<T> entities = new CopyOnWriteArrayList<>();

    public void add(T entity) {
        entities.add(entity);
    }

    public boolean removeElement(T element) {
        return entities.remove(element);
    }

    public List<T> getAll() {
        return List.copyOf(entities);
    }

    public List<T> filteredByAge(int min, int max) {
        return entities.stream().filter(entity -> entity.getAge() >= min && entity.getAge() <= max)
                .collect(Collectors.toList());
    }
    public List<T> filteredByName(String name) {
        return entities.stream().filter(entity -> entity.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<T> filteredByStatus(boolean isActive) {
        return entities.stream().filter(entity -> entity.isActive())
                .collect(Collectors.toList());
    }

}
