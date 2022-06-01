package com.example.v3;

import java.lang.reflect.Member;
import java.util.ArrayList;

public class Data_join {

    int id;
    String email;
    String passwd;
    String name;
    String content;
    String num;


    public Data_join(String email, String passwd, String name, String content, String num) {
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.content = content;
        this.num = num;
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
}
