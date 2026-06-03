package practice_7;

import java.lang.reflect.Array;
import java.util.List;

public class AdditionalTasks {
    //2
    public <T> void printList(List<T> list) {
        for (T element : list) {
            System.out.println(element);
        }
    }

}
