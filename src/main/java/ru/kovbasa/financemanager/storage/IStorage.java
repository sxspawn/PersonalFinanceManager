package ru.kovbasa.financemanager.storage;

import ru.kovbasa.financemanager.model.ShoppingList;

public interface IStorage {

    void saveData(ShoppingList data);

    ShoppingList loadData();

    boolean isStorageExists();
}
