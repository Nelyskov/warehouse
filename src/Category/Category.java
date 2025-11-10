package Category;

import Products.Product;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Product> products;
    public Category(String category)
    {
        this.name = category;
        products = new ArrayList<Product>();
    }
    public String getName()
    {
        return name;
    }
    public void addProductToCategory(Product product)
    {
        products.add(product);
    }

    public void deleteProductFromCategory(Product product)
    {
        products.remove(product);
    }

    public List<Product> getAvailableProducts()
    {
        List<Product> availableProducts = new ArrayList<>();
        for(Product product : products)
        {
            if(product.getStockQuantity() > 0)
                availableProducts.add(product);
        }

        return availableProducts;
    }

    public double getCategorieValue()
    {
        double value = 0;
        for(Product product : products)
        {
            value += product.getPrice() * product.getStockQuantity();
        }
        return value;
    }
}

