package ru.kovbasa.financemanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.kovbasa.financemanager.model.Shopping;

import java.util.GregorianCalendar;
import java.util.Map;

public class StatisticsTotalTest {


    @Test
    public void testPrepareStatistics() {
        StatisticsTotal statisticsTotal = new StatisticsTotal();

        Shopping shopping = new Shopping();
        shopping.setTitle("булка");
        GregorianCalendar calendar = new GregorianCalendar();
        shopping.setDate(calendar.getTime());
        shopping.setSum(100);
        statisticsTotal.process(shopping);
        System.out.println(shopping);

        Map<String, String> result = statisticsTotal.prepareStatistics();
        Assertions.assertEquals("{\"category\":\"еда\",\"sum\":\"100\"}", result.get("maxCategory"));
    }
}