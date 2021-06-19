package com.example.gramlogin;

public class ServiceModel {
    String fees, servicename;

    public ServiceModel() {
    }

    public ServiceModel(String fees, String servicename) {
        this.fees = fees;
        this.servicename = servicename;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }
}
