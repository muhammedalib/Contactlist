package com.muhammed.noteapp;

public class ExampleRegister {
    String id,name,image,number,mail,address,birthday,upload,upgrade;

    public ExampleRegister(String id, String name, String image, String number, String mail, String address, String birthday, String upload, String upgrade) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.number = number;
        this.mail = mail;
        this.address = address;
        this.birthday = birthday;
        this.upload = upload;
        this.upgrade = upgrade;
    }

    public ExampleRegister() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getNumber() {
        return number;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getUpload() {
        return upload;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }
}
