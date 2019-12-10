package com.example.ellie.mobiledevassignment;

import java.util.List;

/**
 * Created by ellie on 10/12/2019.
 */

public class EventItem {
    private List<TextItem> summary;
    private List<TextItem> title;

    public List<TextItem> getSummary() {
        return summary;
    }

    public void setSummary(List<TextItem> summary) {
        this.summary = summary;
    }

    public List<TextItem> getTitle() {
        return title;
    }

    public void setTitle(List<TextItem> title) {
        this.title = title;
    }
}