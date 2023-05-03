package ru.kovbasa.financemanager.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.kovbasa.financemanager.settings.Settings;
import ru.kovbasa.financemanager.settings.SettingsManager;

public class CategoryList {

    private static Map<String, String> categories;

    static {
        Settings settings = SettingsManager.getSettings();

        categories = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(settings.getCategoriesFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                categories.put(parts[0].trim().toLowerCase(), parts[1].trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла категорий: " + e.getMessage());
        }
    }

    private CategoryList() {
    }

    public static String getCategory(String title) {
        return categories.getOrDefault(title, "другое");
    }

    public static List<String> getProducts() {
        return new ArrayList<>(categories.keySet());
    }

}
