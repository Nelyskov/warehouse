package Products;

import java.time.LocalDate;

public class PerishableProduct extends Product {
    private LocalDate date;

    public PerishableProduct(int id, String name, double price, int stockQuantity, LocalDate date)
    {
        super(id, name, price, stockQuantity);
        this.date = date;
    }
    public void setDate(LocalDate date)
    {
        if(!this.date.isBefore(date))
        {
            System.out.println("the date cannot be today or earlier");
        }
        else
        {
            this.date = date;
        }
    }
    public LocalDate getDate()
    {
        return date;
    }

    @Override
    public String getDescription() {
        return String.format("Товар: %s, Цена: (%.2f), годен до: %d", getName(), getPrice(), getDate());
    }
}
