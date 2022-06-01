package com.example.v3;

public class Data_JobSeekerjoin {

    int id;
    String email;
    String passwd;
    String name;
    String content;
    String num;
    String sex;
    String age;

    //photo
    String photo;
    //randamNum
    int randamNum;
    //title
    String title;
    //임금
    String pay;
    //지원상태
    String status;
    //address
    String address;


    public Data_JobSeekerjoin(String email, String passwd, String name, String content, String num, String sex, String age, String photo, int randamNum, String title, String pay, String status, String address) {
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.content = content;
        this.num = num;
        this.sex = sex;
        this.age = age;
        this.photo = photo;
        this.randamNum = randamNum;
        this.title = title;
        this.pay = pay;
        this.status = status;
        this.address = address;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRandamNum() {
        return randamNum;
    }

    public void setRandamNum(int randamNum) {
        this.randamNum = randamNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
