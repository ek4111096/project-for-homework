package practice_12;

import org.junit.jupiter.api.Test;
import practice_12.task5_inventoryService.InventoryService;
import practice_12.task5_inventoryService.OutOfStockException;
import practice_12.task5_inventoryService.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * добавление новой категории в пустой список
 * добавление в существующую категорию и пустой список
 * добавление в НЕпустой список
 * проверить размер
 * поверить, что продукт содержится в списке
 * проверить, сто добавилось то что передали
 */
public class InventoryServiceTest {

    @Test
    public void addProductTest() {
        InventoryService service = new InventoryService();
        service.isInventoryOpen = false;
        Product phone = new Product("Moto", "phone", 1000);
        Product phoneNewObject = new Product("Moto", "phone", 1000);
        assertThrows(OutOfStockException.class, () -> {
            service.addProduct(phone);
        });
        service.isInventoryOpen = true;
        service.addProduct(phone);
        assertFalse(service.allProducts.isEmpty());
        assertEquals(1, service.allProducts.size());
        assertTrue(service.allProducts.containsKey("phone"));
        Product actualResult = service.allProducts.get("phone").get(0);
        assertEquals(phoneNewObject, actualResult);
        Product tv = new Product("Samsung", "TV", 500);
        Product tvNewObject = new Product("Samsung", "TV", 500);
        service.addProduct(tv);
        assertEquals(2, service.allProducts.size());
        assertTrue(service.allProducts.containsKey("TV"));
        assertEquals(tvNewObject, service.allProducts.get("TV").getLast());
    }

    @Test
    public void getProductsByCategoryTest() {
        InventoryService service = new InventoryService();
        Product phone = new Product("Moto", "phone", 1000);
        Product phoneNewObject = new Product("Moto", "phone", 1000);
        service.addProduct(phone);
        List<Product> actualResult = service.getProductsByCategory(phone);
        assertEquals(phoneNewObject, actualResult.getFirst());
        Product tv = new Product("Tuvio", "TV", 500);
        assertThrows(OutOfStockException.class, () -> {
            service.getProductsByCategory(tv);
        });

    }

    @Test
    public void filterdProductsByPriceTest() {
        InventoryService service = new InventoryService();
        Product phone1 = new Product("Moto", "phone", 102);
        Product phone2 = new Product("Iphone", "phone", 100);
        Product phone3 = new Product("Samsung", "phone", 101);
        Product phone4 = new Product("Honor", "phone", 0);
        Product phone5 = new Product("Siemens", "phone", 99);
        service.addProduct(phone1);
        service.addProduct(phone2);
        service.addProduct(phone3);
        service.addProduct(phone4);
        service.addProduct(phone5);
        service.filterdProductsByPrice(phone1);
        List<Product> list = service.filterdProductsByPrice(phone1);
        assertEquals(2, list.size());
        assertTrue(list.contains(phone1));
        assertTrue(list.contains(phone3));
        assertFalse(list.contains(phone2));

    }
}
