package com.app.toyo.youtubevideosapp;

/**
 * Created by Lenovo on 27-05-2019.
 */

public class User {
    private String title,desc,link;

    public User() {
    }

    public User(String title, String desc, String link) {
        this.title = title;
        this.desc = desc;
        this.link = link;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}