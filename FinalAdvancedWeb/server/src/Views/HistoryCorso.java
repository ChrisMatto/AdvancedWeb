package Views;

public class HistoryCorso {

    private int year;
    private String url;

    public HistoryCorso(int year, String url) {
        this.year = year;
        this.url = url;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
