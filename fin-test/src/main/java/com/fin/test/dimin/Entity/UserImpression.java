package com.fin.test.dimin.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserImpression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String user_impression_id;
    private String user_id;
    private String user_impression;

    public String getUser_impression_id() {
        return user_impression_id;
    }

    public void setUser_impression_id(String user_impression_id) {
        this.user_impression_id = user_impression_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_impression() {
        return user_impression;
    }

    public void setUser_impression(String user_impression) {
        this.user_impression = user_impression;
    }
}
