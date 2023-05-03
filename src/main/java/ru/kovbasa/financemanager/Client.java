package ru.kovbasa.financemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import ru.kovbasa.financemanager.model.Shopping;
import ru.kovbasa.financemanager.services.CategoryList;
import ru.kovbasa.financemanager.settings.Settings;
import ru.kovbasa.financemanager.settings.SettingsManager;

public class Client {

    public static void main(String[] args) {
        Settings settings = SettingsManager.getSettings();

        Random random = new Random(System.nanoTime());
        List<String> products = CategoryList.getProducts();

        int maxPastDate = 365 * 5;
        int maxSum = 200;
        int cnt = 100;

        for (int i = 0; i < cnt; i++) {
            System.out.println("Request: " + (i + 1));
            try (Socket clientSocket = new Socket("localhost", settings.getSocket())) {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                Shopping shopping = new Shopping();
                shopping.setTitle(products.get(random.nextInt(products.size())));
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.add(Calendar.DAY_OF_YEAR, -random.nextInt(maxPastDate));
                shopping.setDate(calendar.getTime());
                shopping.setSum(random.nextInt(maxSum) + 1);
                System.out.println(shopping);

                String json = Constants.GSON.toJson(shopping);

                out.write(json + "\n");
                out.flush();

                String response = in.readLine();
                System.out.println(response);
                System.out.println();
            } catch (IOException e) {
                System.err.println(e);
                e.printStackTrace();
            }
        }
    }
}