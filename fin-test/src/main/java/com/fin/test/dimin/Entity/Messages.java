package com.fin.test.dimin.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Messages {
    @Id
    @GeneratedValue
    private String id;
    private String formuser_id;
    private String touser_id;
    private Date message_time;
    private String type;
    private String infor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormuser_id() {
        return formuser_id;
    }

    public void setFormuser_id(String formuser_id) {
        this.formuser_id = formuser_id;
    }

    public String getTouser_id() {
        return touser_id;
    }

    public void setTouser_id(String touser_id) {
        this.touser_id = touser_id;
    }

    public Date getMessagetime() {
        return message_time;
    }

    public void setMessagetime(Date message_time) {
        this.message_time = message_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfor() {
        return infor;
    }

    public void setInfor(String infor) {
        this.infor = infor;
    }
}
