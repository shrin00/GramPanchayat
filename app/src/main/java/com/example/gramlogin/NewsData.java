package com.example.gramlogin;

public class NewsData {
    String heading, desc;

    public NewsData(String heading, String desc) {
        this.heading = heading;
        this.desc = desc;
    }

    public String getHeading() {
        return heading;
    }

    public String getDesc() {
        return desc;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
