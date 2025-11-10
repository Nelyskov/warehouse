package Products;

public class SimpleProduct extends Product {

    public SimpleProduct(int id, String name, double price, int stockQuantity)
    {
        super(id, name, price, stockQuantity);
    }
    @Override
    public String getDescription() {
        return String.format("Товар: %s, Цена: (%.2f)", getName(), getPrice());
    }
}

