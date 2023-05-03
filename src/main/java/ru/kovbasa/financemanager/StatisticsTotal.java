package ru.kovbasa.financemanager;

import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import ru.kovbasa.financemanager.statistics.StatisticsYear;
import ru.kovbasa.financemanager.model.Shopping;
import ru.kovbasa.financemanager.model.ShoppingList;
import ru.kovbasa.financemanager.services.CategoryList;

public class StatisticsTotal {

    private Map<String, Integer> statistics;
    private Map<Integer, StatisticsYear> items;

    public StatisticsTotal() {
        this.statistics = new HashMap<>();
        this.items = new HashMap<>();
    }

    public void process(Shopping shopping) {
        String category = CategoryList.getCategory(shopping.getTitle());

        statistics.compute(category, (key, value) -> value == null ? shopping.getSum() : value + shopping.getSum());

        StatisticsYear statisticsYear = getStatsYear(shopping.getDate().getYear() + 1900);
        statisticsYear.process(shopping);
    }

    public void process(ShoppingList shoppingList) {
        for (Shopping shopping : shoppingList.getItems()) {
            process(shopping);
        }
    }

    public StatisticsYear getStatsYear(int year) {
        items.putIfAbsent(year, new StatisticsYear());
        return items.get(year);
    }

    public Map<String, String> prepareStatistics() {
        Map<String, String> result = new LinkedHashMap<>();

        Map.Entry<String, Integer> entry = statistics.entrySet().stream().max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>(CategoryList.getCategory(null), 0));

        Map<String, String> map = new LinkedHashMap<>();
        map.put("category", entry.getKey());
        map.put("sum", Integer.toString(entry.getValue()));

        result.put("maxCategory", Constants.GSON.toJson(map));

        return result;
    }

    public Map<String, String> prepareStatistics(Date date) {
        Map<String, String> result = prepareStatistics();

        StatisticsYear statisticsYear = getStatsYear(date.getYear() + 1900);
        result.putAll(statisticsYear.prepareStatistics(date));

        return result;
    }
}
