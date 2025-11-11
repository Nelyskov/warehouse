import Category.Category;
import Exceptions.InsufficientStockException;
import Exceptions.ProductNotFoundException;
import Handler.DataHandler;
import Handler.WarehouseHandler;
import Products.*;
import Warehouse.Warehouse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Warehouse warehouse = new Warehouse("Warehouse");
        DataHandler dataHandler = new DataHandler();
        Scanner scanner = new Scanner(System.in);
        WarehouseHandler warehouseHandler = new WarehouseHandler(warehouse, dataHandler);

        while (true)
        {
            System.out.println("=== Warehouse menu === \n");
            System.out.println("1. Добавить продукт");
            System.out.println("2. Добавить набор продуктов");
            System.out.println("3. Удалить продукт");
            System.out.println("4. Найти продукт по ID");
            System.out.println("5. Найти продукт по имени");
            System.out.println("6. Зарезервировать продукт");
            System.out.println("7. Добавить категорию продуктов");
            System.out.println("8. Удалить категорию продуктов");
            System.out.println("9. Узнать стоимость инветоря");
            System.out.println("10. Сформировать заказ");
            System.out.println("11. Оформить заказ");
            System.out.println("12. Посмотреть все продукты");
            System.out.println("13. Загрузить данные");
            System.out.println("14. Сохранить данные");
            System.out.println("15. Сохранить и выйти");


            System.out.println("\n Введите число и нажмите Enter:");
            int n = scanner.nextInt(); scanner.nextLine();

            switch (n)
            {
                case 1:
                    warehouseHandler.addProductToWarehouse();
                    break;
                case 2:
                    warehouseHandler.showAllProducts();
                    warehouseHandler.addBundleProduct();
                    break;
                case 3:
                    warehouseHandler.removeProductFromWarehouse();
                    break;
                case 4:
                    try
                    {
                        warehouseHandler.findProductByID();
                    }
                    catch (ProductNotFoundException e)
                    {
                        System.out.println(e);
                    }
                    break;
                case 5:
                    try
                    {
                        warehouseHandler.findProductByName();
                    }
                    catch (ProductNotFoundException e)
                    {
                        System.out.println(e);
                    }
                    break;
                case 6:
                    try
                    {
                        warehouseHandler.reserveProduct();
                    }
                    catch (InsufficientStockException e)
                    {
                        System.out.println(e);
                    }
                    break;
                case 7:
                    warehouseHandler.addCategory();
                    break;
                case 8:
                    warehouseHandler.removeCategory();
                    break;
                case 9:
                    warehouseHandler.getInventoryValue();
                    break;
                case 10:
                    warehouseHandler.createOrder();
                    break;
                case 11:
                    warehouseHandler.processOrder();
                case 12:
                    warehouseHandler.showAllProducts();
                    break;
                case 13:
                        warehouse = dataHandler.Load();
                    break;
                case 14:
                    dataHandler.Save(warehouse);
                    break;
                case 15:
                    dataHandler.Save(warehouse);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный ввод. Нажмите Enter, чтобы продолжить");
                    char ch = scanner.next().charAt(0);
                    break;
            }
        }
    }




}