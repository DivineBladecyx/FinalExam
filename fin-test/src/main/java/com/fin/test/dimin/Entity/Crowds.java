package com.fin.test.dimin.Entity;

import javax.persistence.*;

@Entity
public class Crowds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String crowd_main_id;
    private String crowd_id;
    private String crowd_owner_id;
    private String crowd_member;

    public String getCrowd_main_id() {
        return crowd_main_id;
    }

    public void setCrowd_main_id(String crowd_main_id) {
        this.crowd_main_id = crowd_main_id;
    }

    public String getCrowd_id() {
        return crowd_id;
    }

    public void setCrowd_id(String crowd_id) {
        this.crowd_id = crowd_id;
    }

    public String getCrowd_owner_id() {
        return crowd_owner_id;
    }

    public void setCrowd_owner_id(String crowd_owner_id) {
        this.crowd_owner_id = crowd_owner_id;
    }

    public String getCrowd_member() {
        return crowd_member;
    }

    public void setCrowd_member(String crowd_member) {
        this.crowd_member = crowd_member;
    }
}
