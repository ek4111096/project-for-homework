package practice_12.task5_inventoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryService {
    public Map<String, List<Product>> allProducts = new HashMap<>();
    public boolean isInventoryOpen = true;

    public void addProduct(Product product) {
        if (!isInventoryOpen) {
            throw new OutOfStockException("Invalid inventory");
        }
        List<Product> oneCategoryProducts = allProducts.get(product.getCategory());
        if (oneCategoryProducts == null) {
            oneCategoryProducts = new ArrayList<>();
            allProducts.put(product.getCategory(), oneCategoryProducts);
        }
        oneCategoryProducts.add(product);
    }

    public List<Product> getProductsByCategory(Product product) {
        String category = product.getCategory();
        if (allProducts.get(category) == null) {
            throw new OutOfStockException("Category is absent");
        }
        return allProducts.get(category);
    }

    public List<Product> filterdProductsByPrice(Product product) {
        List<Product> productsFiltered = getProductsByCategory(product);
        return productsFiltered.stream()
                .filter(p -> p.getPrice() > product.getPrice())
                .collect(Collectors.toList());
    }
}
