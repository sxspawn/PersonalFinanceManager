package ru.kovbasa.financemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import ru.kovbasa.financemanager.model.Shopping;
import ru.kovbasa.financemanager.model.ShoppingList;
import ru.kovbasa.financemanager.settings.Settings;
import ru.kovbasa.financemanager.settings.SettingsManager;
import ru.kovbasa.financemanager.storage.IStorage;
import ru.kovbasa.financemanager.storage.StorageFactory;

public class Server {

    public static void main(String[] args) {
        Settings settings = SettingsManager.getSettings();

        IStorage storageIn = StorageFactory.getInstance(settings.getDataInFormat(), settings.getDataInFile());
        IStorage storageOut = StorageFactory.getInstance(settings.getDataOutFormat(), settings.getDataOutFile());

        ShoppingList shoppingList = storageIn.loadData();
        if (!settings.getDataInFormat().equals(settings.getDataOutFormat())) {
            storageOut.saveData(shoppingList);
        }

        StatisticsTotal total = new StatisticsTotal();
        total.process(shoppingList);

        try (ServerSocket serverSocket = new ServerSocket(settings.getSocket())) {
            System.out.println("Сервер запущен!");

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String inputLine = in.readLine();
                    System.out.println("Request: '" + inputLine + "'");
                    Shopping shopping = Constants.GSON.fromJson(inputLine, Shopping.class);
                    total.process(shopping);
                    shoppingList.addShopping(shopping);
                    storageOut.saveData(shoppingList);

                    Writer out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    out.write(total.prepareStatistics(shopping.getDate()).toString());
                    out.flush();
                } catch (Exception e) {
                    System.err.println("Ошибка при обработке запроса: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }

}