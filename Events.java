package com.example.ellie.mobiledevassignment;

import java.util.List;

public class Events {
    private int count;
    private int page;
    private int pages;
    private List<EventItem> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int page) {
        this.pages = pages;
    }

    public List<EventItem> getResults() {
        return results;
    }

    public void setResults(List<EventItem> eventItems) {
        this.results = eventItems;
    }

}