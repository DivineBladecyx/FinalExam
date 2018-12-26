package com.fin.test.dimin.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String f_id;
    private String f_user_id;
    private String f_friend_id;
    private String f_group_id;
    private String f_group_name;

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getF_user_id() {
        return f_user_id;
    }

    public void setF_user_id(String f_user_id) {
        this.f_user_id = f_user_id;
    }

    public String getF_friend_id() {
        return f_friend_id;
    }

    public void setF_friend_id(String f_friend_id) {
        this.f_friend_id = f_friend_id;
    }

    public String getF_group_id() {
        return f_group_id;
    }

    public void setF_group_id(String f_group_id) {
        this.f_group_id = f_group_id;
    }

    public String getF_group_name() {
        return f_group_name;
    }

    public void setF_group_name(String f_group_name) {
        this.f_group_name = f_group_name;
    }
}
