package com.example.v3;

public class Data_joblist {

    String title;
    String cropinfo;
    String pay;

    String ID;
    String datestart;
    String dateend;
    String person;
    String content;
    //photo
    String photo;
    //num
    String num;
    //난수
    int randomNum;
    //address & map
    String address;
    String latitude;
    String longitude;


    public Data_joblist(String ID, String title, String cropinfo, String datestart, String dateend, String pay, String person, String content, String photo,  String num, int randomNum,  String address, String latitude, String longitude) {
        this.ID = ID;
        this.title = title;
        this.cropinfo = cropinfo;
        this.datestart = datestart;
        this.dateend = dateend;
        this.pay = pay;
        this.person = person;
        this.content = content;
        //photo
        this.photo = photo;
        //num
        this.num = num;
        //난수
        this.randomNum = randomNum;
        //address & map
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCropinfo() {
        return cropinfo;
    }

    public void setCropinfo(String cropinfo) {
        this.cropinfo = cropinfo;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDatestart() {
        return datestart;
    }

    public void setDatestart(String datestart) {
        this.datestart = datestart;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dateend) {
        this.dateend = dateend;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(int randomNum) {
        this.randomNum = randomNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
