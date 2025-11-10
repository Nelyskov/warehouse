package Handler;

import Category.Category;
import Order.Order;
import Products.BundleProduct;
import Products.PerishableProduct;
import Products.Product;
import Products.SimpleProduct;
import Warehouse.Warehouse;

import java.time.LocalDate;
import java.util.*;

public class WarehouseHandler {
    private Warehouse warehouse;
    private DataHandler dataHandler;
    private List<Order> orders = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    public WarehouseHandler(Warehouse warehouse, DataHandler dataHandler)
    {
        this.warehouse = warehouse;
        this.dataHandler = dataHandler;
    }
    public void findProductByID()
    {
        System.out.println("Введите ID товара (1)");
        int id = scanner.nextInt(); scanner.nextLine();
        warehouse.findProductByID(id);
    }

    public void findProductByName()
    {
        System.out.println("Введите имя товара (Молоко)");
        String name= scanner.nextLine();
        warehouse.findProductsByName(name);
    }

    public void reserveProduct()
    {
        System.out.println("Введите ID товара (1)");
        int id = scanner.nextInt(); scanner.nextLine();
        System.out.println("Введите количество товаров для резервирования (10)");
        int quantity = scanner.nextInt(); scanner.nextLine();
        warehouse.reserveProduct(id, quantity);
    }

    public void addCategory()
    {
        System.out.println("Введите категорию товара");
        String categoryName6 = scanner.nextLine();
        Category category = new Category(categoryName6);
        warehouse.addCategory(category);
    }

    public void removeCategory()
    {
        System.out.println("Введите категорию товара");
        String categoryName = scanner.nextLine();
        Category category = new Category(categoryName);
        warehouse.removeCategory(category);
    }

    public void getInventoryValue()
    {
        Map<String, Double> values = warehouse.getInventoryValue();
        if(!values.isEmpty())
        {
            for(Map.Entry<String, Double> cat: values.entrySet())
            {
                String key = cat.getKey();
                Double value = cat.getValue();
                System.out.printf("Категория: %s, Стоимость: %.2f", key, value);
            }
        }
        else
        {
            System.out.println("Инвентарь пуст");
        }
    }

    public void removeProductFromWarehouse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID товара (1)");
        int id = scanner.nextInt(); scanner.nextLine();

        Product product = warehouse.findProductByID(id);
        List<Category> categories = warehouse.getCategories();
        List<Product> products;
        for(Category category : categories)
        {
            products = category.getAvailableProducts();
            if(products.contains(product))
            {
                category.deleteProductFromCategory(product);
                break;
            }
        }
        warehouse.removeProduct(id);
    }

    public void addProductToWarehouse() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите категорию товара");
        String categoryName = scanner.nextLine();
        Category category;
        List<Category> categories = warehouse.getCategories();
        if(categories.contains(categoryName.toLowerCase()))
        {
            category = (Category) categories.stream().filter(c -> c.getName().equalsIgnoreCase(categoryName));
        }
        else
        {
            category = new Category(categoryName.toLowerCase());
        }
        warehouse.addCategory(category);

        Product product = null;
        System.out.println("Введите ID товара (1)");
        int id = scanner.nextInt(); scanner.nextLine();

        System.out.println("Введите наименование товара");
        String name = scanner.nextLine();

        System.out.println("Введите цену товара (100.00)");
        double price = scanner.nextDouble(); scanner.nextLine();

        System.out.println("Введите количество товара (100)");
        int quantity = scanner.nextInt(); scanner.nextLine();

        System.out.println("Введите дату, если у товара есть срок годность, в формате гггг-мм-дд или нажмите Enter");
        String date = scanner.nextLine();

        LocalDate localDate = null;
        if(!date.isEmpty())
        {
            try
            {
                localDate = LocalDate.parse(date);
                product = new PerishableProduct(id, name, price, quantity, localDate);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else
        {
            product = new SimpleProduct(id, name, price, quantity);
        }
        product = new SimpleProduct(id, name, price, quantity);

        if(warehouse.findProductByID(id) != null)
        {
            Product existingProduct = warehouse.findProductByID(id);
            int currnetQuantity = existingProduct.getStockQuantity();
            existingProduct.setStockQuantity(currnetQuantity + quantity);
            System.out.println("Товар с ID существует");
            return;
        }

        category.addProductToCategory(product);
        warehouse.addProduct(product);

        System.out.println("Товар добавлен.");
    }

    public void showAllProducts()
    {
        Map<Integer, Product> products = warehouse.getAllProducts();
        if(!products .isEmpty()) {
            for (Map.Entry<Integer, Product> prod : products.entrySet()) {
                System.out.println(prod.getValue().getDescription());
            }
        }
        else
        {
            System.out.println("Нет информации для отображения");
        }
    }

    public void addBundleProduct()
    {
        System.out.println("Введите имя набора");
        String name = scanner.nextLine();

        System.out.println("Введите ID набора (1)");
        int id = scanner.nextInt(); scanner.nextLine();

        System.out.println("Введите ID товаров, которые необходимо добавить в набор (Например, 1 13 15)");
        String[] str = scanner.nextLine().split("\\s+");
        int[] ids = new int[str.length];
        try {
            for(int i = 0; i < str.length; i++)
                ids[i] = Integer.parseInt(str[i]);
        }
        catch (NumberFormatException e)
        {
            System.out.println(e);
        }

        List<Product> products = new ArrayList<>();
        Product product;
        for(int i = 0; i < ids.length; i++)
        {
            product = warehouse.findProductByID(ids[i]);
            products.add(product);
        }

        warehouse.addProduct(new BundleProduct(id, name, products));
        System.out.println("Набор продуктов добавлен");
    }

    public void createOrder()
    {

        System.out.println("Введите ID товаров, которые необходимо добавить в набор (Например, 1 13 15)");
        String[] str = scanner.nextLine().split("\\s+");
        int[] ids = new int[str.length];
        try {
            for(int i = 0; i < str.length; i++)
                ids[i] = Integer.parseInt(str[i]);
        }
        catch (NumberFormatException e)
        {
            System.out.println(e);
        }

        System.out.println("Введите количество товаров, которые необходимо добавить в набор (Например, 1 2 4)");
        str = scanner.nextLine().split("\\s+");
        int[] quanity = new int[str.length];

        try {
            for(int i = 0; i < quanity.length; i++)
                quanity[i] = Integer.parseInt(str[i]);
        }
        catch (NumberFormatException e)
        {
            System.out.println(e);
        }

        HashMap<Product, Integer> products = new HashMap<>();
        for(int i = 0; i < ids.length; i++)
        {
            products.put(warehouse.findProductByID(i), quanity[i]);
        }

        Order order = new Order(orders.size() + 1, products);
        orders.add(order);
        System.out.println("Заказ создан");
    }

    public void processOrder()
    {
        int index = 0;
        for(Order order : orders)
        {
            System.out.printf("Index: " + index + " " + order.getDescription());
        }
        System.out.println("Введите индекс заказа");
        int id = scanner.nextInt(); scanner.nextLine();

        Order order = orders.get(index);
        order.processOrder();
    }


}
