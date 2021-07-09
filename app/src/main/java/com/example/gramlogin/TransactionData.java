package com.example.gramlogin;

public class TransactionData {
    String amount, date, email, result, time;

    public TransactionData() {
    }

    public TransactionData(String amount, String date, String email, String result, String time) {
        this.amount = amount;
        this.date = date;
        this.email = email;
        this.result = result;
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
