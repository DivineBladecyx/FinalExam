package com.fin.test.dimin.Entity;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String user_id;
    private String user_password;
    private String user_nickname;
    private int user_age;
    private int user_sex;
    private Date user_registime;
    private String user_comments;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public int getUser_age() {
        return user_age;
    }

    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public Date getUser_registime() {
        return user_registime;
    }

    public void setUser_registime(Date user_registime) {
        this.user_registime = user_registime;
    }

    public String getUser_comments() {
        return user_comments;
    }

    public void setUser_comments(String user_comments) {
        this.user_comments = user_comments;
    }
}


