package Products;

import Exceptions.InsufficientStockException;

import java.util.List;

public class BundleProduct extends Product{

    List<Product> includedProducts;
    private int discount = 0;

    public BundleProduct(int id, String name, List<Product> products)
    {
        super(id, "Product bundle: " + name, 0, 0);
        includedProducts = products;
        this.setPrice(getBundlePrice());

    }

    public double getBundlePrice()
    {
        if(includedProducts != null)
        {
            return includedProducts.stream()
                    .mapToDouble(x -> x.getPrice() * (1 - discount/100.0)).sum();
        }
        else
            return 0.0;
    }

    public int getDiscount()
    {
        return discount;
    }

    public void setDiscount(int discount)
    {
        if(discount >= 0 && discount <= 100)
        {
            this.discount = discount;
        }
        else
        {
            System.out.printf("Discount cant be %d", discount);
        }
    }

    @Override
    public boolean reserve(int quantity)
    {
        for(Product product : includedProducts)
        {
            if(product.getStockQuantity() < quantity)
                throw new InsufficientStockException(String.format("Нельзя зарезирвировать %s", product.getName()));
        }
        for(Product  product : includedProducts)
        {
            product.reserve(quantity);
        }
        return true;
    }

    @Override
    public String getDescription() {
        return String.format("Комплект продуктов: %s, Количество продуктов: %d, Цена со скидкой: (%.2f), Скидка: %d",
                getName(), includedProducts.size(), getBundlePrice(), discount);
    }
}
