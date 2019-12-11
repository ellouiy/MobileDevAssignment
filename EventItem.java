package com.example.ellie.mobiledevassignment;

import org.w3c.dom.Text;

import java.util.List;


public class EventItem {
    private List<TextItem> summary;
    private List<TextItem> title;
    private String eventDate;
    private String uri;


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    private List<TextItem> location;

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public List<TextItem> getLocation() {
        return location;
    }

    public void setLocation(List<TextItem> location) {
        this.location = location;
    }

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