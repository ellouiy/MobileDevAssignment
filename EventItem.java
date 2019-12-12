package com.example.ellie.mobiledevassignment;

import java.util.List;


public class EventItem {
    private String body;
    private String title;
    private List<TextItem> source;
    private String date;
    private String url;
    private String img;


    public String getUrl() {
        return url;
    }

    public void setUrl(String uri) {
        this.url = url;
    }


    private List<TextItem> location;

    public String getDate() {
        return date;
    }

    public void setDate(String eventDate) {
        this.date = date;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TextItem> getSource() {
        return source;
    }

    public void setSource(List<TextItem> source) {
        this.source = source;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}