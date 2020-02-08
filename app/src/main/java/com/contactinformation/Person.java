package com.contactinformation;

public class Person {
    private String Pname, Pcontact,Paddress;

    public void setPname(String name){
        this.Pname = name;
    }
    public String getPname() {
        return Pname;
    }
    public void setPcontact(String contact){
        this.Pcontact = contact;
    }
    public String getPcontact() {
        return Pcontact;
    }
    public void setPaddress(String address){
        this.Paddress = address;
    }
    public String getPaddress() {
        return Paddress;
    }
}
