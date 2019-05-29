package com.app.toyo.youtubevideosapp;

public class Blog {
    private String desc;
    private String link;
    private String title;


    public Blog(String desc, String link, String title) {
        this.desc = desc;
        this.link = link;
        this.title = title;
    }
    public Blog(){

    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
