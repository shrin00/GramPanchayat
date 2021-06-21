package com.example.gramlogin;

public class UserProfile {
    String name, contactNo, dob, sex, aadhar, address, pincode;
    boolean isAdmin;

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public UserProfile(String name, String contactNo, String dob, String sex, String aadhar, String address, String pincode, boolean isAdmin) {
        this.name = name;
        this.contactNo = contactNo;
        this.dob = dob;
        this.sex = sex;
        this.aadhar = aadhar;
        this.address = address;
        this.pincode = pincode;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
