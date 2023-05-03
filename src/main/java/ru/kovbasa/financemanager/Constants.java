package ru.kovbasa.financemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Constants {

    public static final String DATE_FORMAT = "yyyy.MM.dd";

    public static final Gson GSON = new GsonBuilder().setDateFormat(DATE_FORMAT).create();

}
