package ru.kovbasa.financemanager.statistics;

import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import ru.kovbasa.financemanager.Constants;
import ru.kovbasa.financemanager.model.Shopping;
import ru.kovbasa.financemanager.services.CategoryList;

public class StatisticsYear {

    private Map<String, Integer> statistics;
    private Map<Integer, StatisticsMonth> items;

    public StatisticsYear() {
        this.statistics = new HashMap<>();
        this.items = new HashMap<>();
    }

    public void process(Shopping shopping) {
        String category = CategoryList.getCategory(shopping.getTitle());

        statistics.compute(category, (key, value) -> value == null ? shopping.getSum() : value + shopping.getSum());

        StatisticsMonth statisticsMonth = getStatsMonth(shopping.getDate().getMonth());
        statisticsMonth.process(shopping);
    }

    public StatisticsMonth getStatsMonth(int month) {
        items.putIfAbsent(month, new StatisticsMonth());
        return items.get(month);
    }

    public Map<String, String> prepareStatistics(Date date) {
        Map<String, String> result = new LinkedHashMap<>();

        Map.Entry<String, Integer> entry = statistics.entrySet().stream().max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>(CategoryList.getCategory(null), 0));

        Map<String, String> map = new LinkedHashMap<>();
        map.put("category", entry.getKey());
        map.put("sum", Integer.toString(entry.getValue()));

        result.put("maxYearCategory", Constants.GSON.toJson(map));

        StatisticsMonth statisticsMonth = getStatsMonth(date.getMonth());
        result.putAll(statisticsMonth.prepareStatistics(date));

        return result;
    }

}
