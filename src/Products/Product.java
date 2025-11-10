package Products;

import Exceptions.InsufficientStockException;
import Interfaces.Reservable;

public abstract class Product implements Reservable {
    private final int id;
    private String name;
    private double price;
    private int stockQuantity;
    private int reserve;

    public Product(int id, String name, double price, int stockQuantity)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    public int getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        if(price >= 0)
            this.price = price;
        else
            System.out.printf("Price (%.2f) can`t be below zero", price);
    }

    public int getStockQuantity()
    {
        return stockQuantity;
    }
    public void setStockQuantity(int setQuantity) { // возможно вернуть bool
        if(setQuantity >= 0)
        {
            stockQuantity = setQuantity;
            System.out.printf("Изменено количество. Новое значение: %d", stockQuantity);
        }
        else
        {
            throw new InsufficientStockException(String.format("Stock quantity (%d) can`t be below zero", setQuantity));
        }
    }
    public boolean reserve(int quantity)
    {
        if(this.stockQuantity >= quantity)
        {
            reserve = quantity;
            stockQuantity -= quantity;
            System.out.println("Товар зарезервирован");
            return true;
        }
        else
        {
            return false;
//            throw new InsufficientStockException(String.format("Can`t reserve %d", quantity));
        }
    }
    public void cancelReserver(int reserve)
    {
        if(this.reserve > 0 && this.reserve > reserve)
        {
            stockQuantity += reserve;
            this.reserve -= reserve;
        }
        else
            System.out.println("Reserve = 0");
    }
    public abstract String getDescription();
}

