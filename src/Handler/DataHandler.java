package Handler;

import Interfaces.Loader;
import Interfaces.Saver;
import Warehouse.Warehouse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;

public class DataHandler implements Saver, Loader {
    private static final String filePath = "WarehouseData.json";
//    ObjectMapper mapper = new ObjectMapper();
//    public void Save(Warehouse warehouse)
//    {
//        try(FileWriter writer = new FileWriter(filePath, false))
//        {
//            String jsonData = mapper.writeValueAsString(warehouse);
//            writer.write(jsonData);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public Warehouse Load() throws FileNotFoundException {
//        try(FileReader reader = new FileReader(filePath))
//        {
//            return mapper.readValue(reader, Warehouse.class);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void Save(Warehouse warehouse) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath)))
        {
            oos.writeObject(warehouse);
            System.out.println("Данные сохранены");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Warehouse Load()
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath)))
        {
            Warehouse warehouse = (Warehouse) ois.readObject();
            System.out.println("Данные загружены");
            return warehouse;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
