package com.saifullahmnsur.contactsbysaifullah;

public class Contact {
    private int ID;
    private String name, number, sex;

    public Contact(int ID, String name, String number, String sex) {
        this.ID = ID;
        this.name = name;
        this.number = number;
        this.sex = sex;
    }
    public Contact(){

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
