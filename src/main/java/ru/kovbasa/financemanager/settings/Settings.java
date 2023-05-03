package ru.kovbasa.financemanager.settings;

public class Settings {

    private String categoriesFile;

    private int socket;

    private String dataInFile;
    private String dataInFormat;

    private String dataOutFile;
    private String dataOutFormat;

    protected Settings() {
        this.categoriesFile = "categories.tsv";

        this.socket = 8989;

        this.dataInFile = "data.bin";
        this.dataInFormat = "bin";

        this.dataOutFile = "data.bin";
        this.dataOutFormat = "bin";
    }

    public String getCategoriesFile() {
        return categoriesFile;
    }

    public void setCategoriesFile(String categoriesFile) {
        this.categoriesFile = categoriesFile;
    }

    public int getSocket() {
        return socket;
    }

    public void setSocket(int socket) {
        this.socket = socket;
    }

    public String getDataInFile() {
        return dataInFile;
    }

    public void setDataInFile(String dataInFile) {
        this.dataInFile = dataInFile;
    }

    public String getDataInFormat() {
        return dataInFormat;
    }

    public void setDataInFormat(String dataInFormat) {
        this.dataInFormat = dataInFormat;
    }

    public String getDataOutFile() {
        return dataOutFile;
    }

    public void setDataOutFile(String dataOutFile) {
        this.dataOutFile = dataOutFile;
    }

    public String getDataOutFormat() {
        return dataOutFormat;
    }

    public void setDataOutFormat(String dataOutFormat) {
        this.dataOutFormat = dataOutFormat;
    }
}
