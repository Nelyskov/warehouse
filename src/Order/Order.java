package Order;

import Products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order {
    private int id;
    private Map<Product, Integer> items;
    // private List<Integer> quantities; // А для чего?
    private OrderStatuses status;

    public Order(int id, Map<Product, Integer> products)
    {
        this.id = id;
        this.items = products;
        this.status = OrderStatuses.NEW;
    }

    public double getTotalPrice()
    {
        if(!items.isEmpty())
        {
            double totalPrice = 0;
            for(Map.Entry<Product, Integer> product : items.entrySet())
            {
                totalPrice += product.getKey().getPrice() * product.getValue();
            }
            return totalPrice;
        }
        else
        {
            return 0;
        }
    }
    public boolean processOrder()
    {
        if(items.isEmpty() || status == OrderStatuses.COMPLETED || status == OrderStatuses.CANCELLED)
            return false;

        for(Map.Entry<Product, Integer> prod : items.entrySet())
        {
            if(prod.getKey().getStockQuantity() < prod.getValue()) {
                System.out.println("Не все товары доступны для покупки");
                return false;
            }
        }

        for(Map.Entry<Product, Integer> prod : items.entrySet())
        {
            prod.getKey().reserve(prod.getValue());
            System.out.println("Все товары доступны для покупки");

        }
        System.out.println("Статус заказа - Completed");
        status = OrderStatuses.COMPLETED;
        return true;
    }
    public void cancelOrder()
    {
        System.out.println("Статус заказа - Отменен");
        status = OrderStatuses.CANCELLED;
    }
    public String getDescription()
    {
        return String.format("Статус заказа %s, его стоимость: %.2f", status, getTotalPrice());
    }
}
