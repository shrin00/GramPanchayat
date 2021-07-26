package com.example.gramlogin;

public class UserApplicationModel {
    String servicename, username, dat;

    public UserApplicationModel() {
    }

    public UserApplicationModel(String servicename, String username, String dat) {
        this.servicename = servicename;
        this.username = username;
        this.dat = dat;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }
}
