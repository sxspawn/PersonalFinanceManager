package ru.kovbasa.financemanager.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Shopping implements Serializable {

    private static final long serialVersionUID = -251769536230358157L;

    private String title;
    private Date date;
    private int sum;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Shopping: {\r\n" +
                "  title: " + title + "\r\n" +
                "  date: " + DateFormat.getInstance().format(date) + "\r\n" +
                "  sum: " + sum + "\r\n}";
    }
}
