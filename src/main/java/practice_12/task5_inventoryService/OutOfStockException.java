package practice_12.task5_inventoryService;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String text) {
        super(text);
    }
}
