package ru.kovbasa.financemanager.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.kovbasa.financemanager.model.ShoppingList;

public class BinStorage implements IStorage {

    private String fileName;

    public BinStorage(String fileName) {
        this.fileName = fileName.endsWith(".bin") ? fileName : fileName + ".bin";
    }

    @Override
    public void saveData(ShoppingList data) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.fileName))) {
            outputStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ShoppingList loadData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.fileName))) {
            return (ShoppingList) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ShoppingList();
    }

    @Override
    public boolean isStorageExists() {
        return new File(this.fileName).exists();
    }
}
