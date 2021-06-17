package com.example.gramlogin;

public class Comment_model {
    String date, name, time, uid, usermsg;

    public Comment_model() {
    }

    public Comment_model(String date, String name, String time, String uid, String usermsg) {
        this.date = date;
        this.name = name;
        this.time = time;
        this.uid = uid;
        this.usermsg = usermsg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsermsg() {
        return usermsg;
    }

    public void setUsermsg(String usermsg) {
        this.usermsg = usermsg;
    }
}
