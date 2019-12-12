package com.example.ellie.mobiledevassignment;

import java.util.List;

public class Articles {
    private int count;
    private int page;
    private int pages;
    private List<EventItem> results;


    public List<EventItem> getResults() {
        return results;
    }

    public void setResults(List<EventItem> eventItems) {
        this.results = eventItems;
    }

}