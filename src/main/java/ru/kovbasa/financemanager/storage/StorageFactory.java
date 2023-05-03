package ru.kovbasa.financemanager.storage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class StorageFactory {

    public static IStorage getInstance(String type, Object... args) {
        if (type == null || type.isBlank()) {
            return null;
        }

        String className = IStorage.class.getPackageName() + "." + type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase() + "Storage";

        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(Arrays.stream(args).map(Object::getClass).toArray(Class[]::new));
            return (IStorage) constructor.newInstance(args);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
