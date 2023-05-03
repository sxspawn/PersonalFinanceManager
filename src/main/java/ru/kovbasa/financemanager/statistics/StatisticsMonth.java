package ru.kovbasa.financemanager.statistics;

import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import ru.kovbasa.financemanager.Constants;
import ru.kovbasa.financemanager.model.Shopping;
import ru.kovbasa.financemanager.services.CategoryList;

public class StatisticsMonth {

    private Map<String, Integer> statistics;
    private Map<Integer, StatisticsDay> items;

    public StatisticsMonth() {
        this.statistics = new HashMap<>();
        this.items = new HashMap<>();
    }

    public void process(Shopping shopping) {
        String category = CategoryList.getCategory(shopping.getTitle());

        statistics.compute(category, (key, value) -> value == null ? shopping.getSum() : value + shopping.getSum());

        StatisticsDay statisticsDay = getStatsDay(shopping.getDate().getDate());
        statisticsDay.process(shopping);
    }

    public StatisticsDay getStatsDay(int day) {
        items.putIfAbsent(day, new StatisticsDay());
        return items.get(day);
    }

    public Map<String, String> prepareStatistics(Date date) {
        Map<String, String> result = new LinkedHashMap<>();

        Map.Entry<String, Integer> entry = statistics.entrySet().stream().max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>(CategoryList.getCategory(null), 0));

        Map<String, String> map = new LinkedHashMap<>();
        map.put("category", entry.getKey());
        map.put("sum", Integer.toString(entry.getValue()));

        result.put("maxMonthCategory", Constants.GSON.toJson(map));

        StatisticsDay statisticsDay = getStatsDay(date.getDate());
        result.putAll(statisticsDay.prepareStatistics(date));

        return result;
    }
}
