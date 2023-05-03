package ru.kovbasa.financemanager.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

import ru.kovbasa.financemanager.Constants;
import ru.kovbasa.financemanager.model.ShoppingList;

public class JsonStorage implements IStorage {

    private String fileName;

    public JsonStorage(String fileName) {
        this.fileName = fileName.endsWith(".json") ? fileName : fileName + ".json";
    }

    @Override
    public void saveData(ShoppingList data) {
        Gson gson = Constants.GSON;
        String json = gson.toJson(data);

        try (PrintWriter out = new PrintWriter(this.fileName)) {
            out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ShoppingList loadData() {
        StringBuffer sb = new StringBuffer();
        try (BufferedReader in = new BufferedReader(new FileReader(this.fileName))) {
            sb.append(in.readLine());

            Gson gson = Constants.GSON;
            return gson.fromJson(sb.toString(), ShoppingList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ShoppingList();
    }

    @Override
    public boolean isStorageExists() {
        return new File(this.fileName).exists();
    }

}
