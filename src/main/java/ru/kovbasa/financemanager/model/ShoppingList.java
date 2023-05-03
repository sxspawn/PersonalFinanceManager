package ru.kovbasa.financemanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList implements Serializable {

    private static final long serialVersionUID = -3976394417460188021L;

    private List<Shopping> items;

    public ShoppingList() {
        this.items = new ArrayList<>();
    }

    public void addShopping(Shopping shopping) {
        items.add(shopping);
    }

    public List<Shopping> getItems() {
        return items;
    }
}
