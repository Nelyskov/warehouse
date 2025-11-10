package Warehouse;

import Category.Category;
import Exceptions.InsufficientStockException;
import Exceptions.ProductNotFoundException;
import Interfaces.Searchable;
import Products.Product;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse implements Searchable, Serializable {
    private String name;
    private Map<Integer, Product> allProducts = new HashMap<>();
    private List<Category> categories = new ArrayList<>();

    public Warehouse(String name)
    {
        this.name = name;
    }
    public Warehouse() {
    }

    public void addProduct(Product product)
    {
//        if(!allProducts.containsValue(product))
//        {
//            for (Map.Entry<Integer, Product> p : allProducts.entrySet()) {
//                if (p.getValue() == null) {
//                    p.setValue(product);
//                    System.out.printf("Продукт %s добавлен. Его номер: %d", product.getName(), p.getKey());
//                    return;
//                }
//            }
//
//            allProducts.put(allProducts.size() + 1, product);
//            System.out.printf("Продукт %s добавлен. Его id: %d",product.getName(),allProducts.size());
//            return;
//        }
//
//        System.out.printf("Продукт %s уже добавлен", product.getName());

        allProducts.put(product.getId(), product);
    }

    public void removeProduct(int id)
    {
        if(allProducts.containsKey(id)) {
            allProducts.remove(id);
            System.out.println("Товар удален");
        }
        else
            throw new ProductNotFoundException(String.format("Исключение при удалении товаоа с id %d", id));
    }

    public Map<Integer, Product> getAllProducts()
    {
        return allProducts;
    }
    public Product findProductByID(int id)
    {
        if(allProducts.containsKey(id))
            return allProducts.get(id);
        else
            throw new ProductNotFoundException(String.format("Товар c id %d не найден", id));
    }

    public List<Product> findProductsByName(String name)
    {
        List<Product> products =  allProducts.values().stream()
                .filter(p -> p.getName().toLowerCase()
                        .contains(name.toLowerCase()))
                .collect(Collectors.toList());

//        return !products.isEmpty() ? products : throw new ProductNotFoundException(String.format("Продукты %s не найдены", name));

        if(!products.isEmpty())
            return  products;
        else
            throw new ProductNotFoundException(String.format("Товаоы %s не найдены", name));
    }

    public boolean reserveProduct(int productId, int quantity)
    {
        if(!allProducts.isEmpty())
        {
            allProducts.get(productId).reserve(quantity);
        }
        throw new InsufficientStockException(String.format("Can`t reserve %d", productId));
    }

    public void addCategory(Category category)
    {
        boolean isCategoryExist = categories.stream().anyMatch(c -> c.getName().equalsIgnoreCase(category.getName()));

        if(isCategoryExist)
        {
            return;
        }
        categories.add(category);
        System.out.println("Категория добавлена");
    }
    public void removeCategory(Category category)
    {
        categories.remove(category);
        System.out.println("Категория удалена");
    }

    public List<Category> getCategories()
    {
        return categories;
    }
    public Map<String, Double> getInventoryValue()
    {
        Map<String, Double> values = new HashMap<>();
        if(!values.isEmpty()) {
            for (Category category : categories) {
                values.put(category.getName(), category.getCategorieValue());
            }
        }
        return values;
    }

    @Override
    public List<Product> search(String query) {
        return List.of();
    }
}

